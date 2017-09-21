package br.uva;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
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
			.antMatchers("/main.html","/main.css","/index.html","/busca.html","/json/**","/","/clinica/**","/registro.html","/clinica.html","/user","/user/**","/login").permitAll()
			.antMatchers("/admin.html","/admin/**").hasAuthority("ADMIN")
          	.anyRequest().authenticated()
          	.and().
	       	csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
      }
      @Override
      public void configure(WebSecurity web) throws Exception {
          web
             .ignoring()
             .antMatchers("/resources/**","/favicon.ico","/static/**","/js/**");
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