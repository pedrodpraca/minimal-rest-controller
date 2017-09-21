package br.uva;

import javax.sql.DataSource;

import org.apache.logging.log4j.core.config.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	

    	@Autowired
    	private BCryptPasswordEncoder bCryptPasswordEncoder;
    	
    	@Autowired
    	private DataSource dataSource;
    	
    	@Value("${spring.queries.users-query}")
    	private String usersQuery;
    	
    	@Value("${spring.queries.roles-query}")
    	private String rolesQuery;
    	
      @Override
      protected void configure(HttpSecurity http) throws Exception {
        http
        	.httpBasic().and()
			.authorizeRequests()
			.antMatchers("/admin.html","/admin/**").hasAuthority("ADMIN")
          	.and().
	       	csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
      }
      @Override
      public void configure(WebSecurity web) throws Exception {
          web
             .ignoring()
             .antMatchers("/resources/**","/static/**");
      }
      
  	@Autowired
  	protected void configureGlobal(AuthenticationManagerBuilder auth)
  			throws Exception {
  		auth.
			jdbcAuthentication()
				.usersByUsernameQuery(usersQuery)
				.authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource)
				.passwordEncoder(bCryptPasswordEncoder);
  		}
}