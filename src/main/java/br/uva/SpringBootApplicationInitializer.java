package br.uva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author notebook
 */
@SpringBootApplication
@Configuration
@ComponentScan
public class SpringBootApplicationInitializer extends SpringBootServletInitializer {
    
    @Override
    protected SpringApplicationBuilder
            configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootApplicationInitializer.class
        );
    }
}