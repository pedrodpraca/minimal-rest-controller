(function () {

	var _lastFetch = null,
		_lastStroke = 0,
		INTERVAL_STROKE_FETCH = 800;

	var app = angular.module('clinica', ['ui.mask', 'ui.bootstrap', 'ui.bootstrap.tpls', 'ngSanitize', 'ngAnimate', 'ngRoute', 'ngCookies']);
	
	app.config(function ($routeProvider, $httpProvider) {

		$routeProvider
			.when("/", {
				templateUrl: "main.html",
				controller: "LoginController",
				controllerAs: 'vm'
			})
			.when("/busca", {
				templateUrl: "busca.html",
				controller: "BuscaController",
			})
			.when("/clinica/:id", {
				templateUrl: "clinica.html",
				controller: "ClinicaController",
			})
			.when("/registro", {
				templateUrl: "registro.html",
			})
			.when("/registro/admin", {
				templateUrl: "registroadmin.html",
				controller: "RegistroAdmController",
				controllerAs: 'vm'
			})
			.when("/admin", {
				templateUrl: "admin.html",
				controller: "AdminController",
				controllerAs: 'vm'
			})
			.otherwise({
				redirectTo: "/"
			});

			$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

			$httpProvider.interceptors.push(function($q, $location, FlashService) {
				return {
					'responseError': function(rejection) {
						if (rejection.status==401) {
							FlashService.Error('Voce presisa estar logado para acessar esta pagina', true);
							$location.path('/');

						}
						else if (rejection.status==404){
							FlashService.Error('Pagina nao encontrada', true);
							$location.path('/');
						}
						return $q.reject(rejection);
					}
				};
			});
	});

	app.run(function ($rootScope, $interval) {

		$rootScope._clearIntervals = function () {
			if (_lastFetch) {
				$interval.clear(_lastFetch);
				_lastFetch = null;
			}
		};
	});

	app.controller('ClinicaController', function ($scope, $http, $rootScope, $location, $routeParams) {
		$rootScope._clearIntervals();

		var id = $routeParams.id;

		$http.get('json/clinica/' + id)
			.then(function (response) {
				$scope._clinica = response.data;

				$scope._clinica.rede = $scope._clinica.tipoAtendimento === "GRATUITO" ? "Pública" : "Privada";

				for (var i = 0; i < $scope._clinica.telefones.length; i++) {
					var tel = $scope._clinica.telefones[i];
					var cut = Math.ceil((tel.length - 2) / 2);
					tel = "(" + tel.substring(0, 2) + ") " + tel.substring(2, 2 + cut) + "-" + tel.substring(2 + cut, tel.length);
					$scope._clinica.telefones[i] = tel;
				}

			}, function () {
				alert("Erro ao recuperar resultados!");
				$location.path("/");
			})
			.then(function () {
				$('#loading').hide();
			});

	});

	app.controller('BuscaController', function ($scope, $http, $rootScope, $interval, $location) {

		$rootScope._clearIntervals();

		$scope.currentPage = 1;
		$scope._resultados = [];

		$scope.abrirProfile = function (id) {
			$location.path("/clinica/" + id);
		};

		$scope.fetchClinica = function (pageNumber) {

			$('.possibilidade-busca').hide();
			$('#loading').show();

			if (_lastFetch === null) {

				if (!pageNumber) {
					pageNumber = 1;
				}

				_lastFetch = $interval(function () {

					var now = new Date().getTime();

					if ((now - _lastStroke) > INTERVAL_STROKE_FETCH) {

						var val = $('#input-busca').val();

						if (!val) {
							val = " ";
						}

						var tipoAtendimento = $('input[name="tipoAtendimento"]:checked').val();

						$('.paginas-resultados').attr('ng-disabled', true);
						$('.possibilidade-busca').hide();
						$('#loading').show();

						$http.get('json/busca/' + val + '/' + pageNumber + '/' + tipoAtendimento)
							.then(function (response) {
								$scope._resultados = response.data;
								$('#loading').hide();
								if (!response.data || response.data.content.length === 0) {
									$('#no-results').show();
								} else {
									$('#tabela-resultados').show();
									$scope.currentPage = pageNumber;
									$scope.totalItems = response.data.totalElements;
									$('.paginas-resultados').show();
									$('.paginas-resultados').attr('ng-disabled', false);
								}

							}, function () {
								alert("Erro ao recuperar resultados!");
							})
							.then(function () {
								$interval.cancel(_lastFetch);
								_lastFetch = null;
								$('#loading').hide();
							});
					}

				}, INTERVAL_STROKE_FETCH * 1.1);
			}

			_lastStroke = new Date().getTime();

			return true;
		};

	});

	app.controller('RegistroAdmController', RegistroAdmController);
	RegistroAdmController.$inject = ['$location', '$rootScope','$window', 'FlashService', 'UserService'];
	function RegistroAdmController($location, $rootScope, $window, FlashService, UserService) {
		var vm = this;

		vm.register = register;

		function register() {
			vm.dataLoading = true;
			UserService.Create(vm.user)
				.then(function (response) {
					vm.dataLoading = false;
					if (response.success) {
						if(response.body.status == 201){
							FlashService.Success('Registro bem sucedido', true);
							$location.path('/');
						}
					} else {
						FlashService.Error(response.message + ': Nome de usuario ja existe', true);
						$window.scrollTo(0, 0);
					}
				});
		}
	}

	app.controller('LoginController', LoginController);
	LoginController.$inject = ['$http', '$location', '$rootScope', 'FlashService', '$window'];
	function LoginController($http, $location, $rootScope, FlashService, $window) {

		var vm = this

		var authenticate = function(credentials, callback) {

			var headers = credentials ? {authorization : "Basic "
				+ btoa(credentials.username + ":" + credentials.password)
			} : {};

			$http.get('login', {headers : headers}).then(function(response) {
			if (response.data.name) {
				$rootScope.authenticated = true;
			} else {
				$rootScope.authenticated = false;
			}
			callback && callback();
			}, function() {
			$rootScope.authenticated = false;
			callback && callback();
			});
		}

		authenticate();

		vm.credentials = {};
		vm.login = function() {
			authenticate(vm.credentials, function() {
			  if ($rootScope.authenticated) {
				$location.path("/admin");
			  } else {
				FlashService.Error('Falha ao logar, verifique se o usuario e a senha estao corretos', true);
			  }
			});
		};

		vm.logout = function() {
			$http.post('/logout', {}).finally(function() {
			  $window.location.reload();
			  $rootScope.authenticated = false;
			});
		  }

	}

	app.controller('AdminController', AdminController);
	AdminController.$inject = ['$rootScope', '$location', '$http', 'UserService','$window'];
    function AdminController( $rootScope, $location, $http,  UserService, $window) {
        var vm = this;
        vm.allUsers = [];

		vm.logout = function() {
			$http.post('/logout', {}).finally(function() {
			  $location.path("/");
			  $window.location.reload();
			  $rootScope.authenticated = false;
			});
		}

        initController();

        function initController() {
            loadCurrentUser(); 
            loadAllUsers();
        }

        function loadCurrentUser() {
			$http.get('login', {}).then(function(response) {
				if (response.data.name) {
					vm.user = response.data.name;
				} else {
					$rootScope.authenticated = false;
				}
			});
        }

        function loadAllUsers() {
            UserService.GetAll()
                .then(function (users) {
                    vm.allUsers = users.body.data;
                });
        }
    }

	app.directive('equals', function () {
		return {
			restrict: 'A', // only activate on element attribute
			require: '?ngModel', // get a hold of NgModelController
			link: function (scope, elem, attrs, ngModel) {
				if (!ngModel) return; // do nothing if no ng-model

				// watch own value and re-validate on change
				scope.$watch(attrs.ngModel, function () {
					validate();
				});

				// observe the other value and re-validate on change
				attrs.$observe('equals', function (val) {
					validate();
				});

				var validate = function () {
					// values
					var val1 = ngModel.$viewValue;
					var val2 = attrs.equals;

					// set validity
					ngModel.$setValidity('equals', !val1 || !val2 || val1 === val2);
				};
			}
		}
	});

})();