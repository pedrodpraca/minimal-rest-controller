(function () {

	var _lastFetch = null,
		_lastStroke = 0,
		INTERVAL_STROKE_FETCH = 800;

	var app = angular.module('clinica', ['ui.bootstrap', 'ui.bootstrap.tpls', 'ngSanitize', 'ngAnimate', 'ngRoute']);

	app.run(function ($rootScope, $interval) {
		$rootScope._clearIntervals = function () {
			if (_lastFetch) {
				$interval.clear(_lastFetch);
				_lastFetch = null;
			}
		};
	});

	app.config(function ($routeProvider, $locationProvider) {

		$routeProvider
			.when("/", {
				templateUrl: "main.html"
			})
			.when("/busca", {
				templateUrl: "busca.html",
				controller: "BuscaController"
			})
			.when("/clinica/:id", {
				templateUrl: "clinica.html",
				controller: "ClinicaController"
			})
			.otherwise({
				template: "<h1>Nada</h1><p>Não há nada aqui!</p>"
			});
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

})();