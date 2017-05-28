package br.uva;

import br.uva.services.gfx.TelaPrincipal;
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

    private static String url = null;
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaPrincipal telaPrincipal = new TelaPrincipal();
                telaPrincipal.setVisible(true);

                new Thread(new Runnable() {
                    public void run() {
                        ConfigurableApplicationContext context = SpringApplication.run(SpringBootApplicationInitializer.class, args);
                        int port = ((TomcatEmbeddedServletContainer) ((AnnotationConfigEmbeddedWebApplicationContext) context).getEmbeddedServletContainer()).getPort();
                        url = "http://localhost:" + port;
                        telaPrincipal.readyState(url);
                    }
                }).start();
            }
        });
    }

    public static String getUrl() {
        return url;
    }
    
    @Override
    protected SpringApplicationBuilder
            configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootApplicationInitializer.class
        );
    }
}