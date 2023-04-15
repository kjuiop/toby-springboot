package io.gig.springboot;

import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author : JAKE
 * @date : 2023/04/16
 */
public class MySpringApplication {

    public static void run(Class<?> applicationClass, String[] args) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {

            @Override
            protected void onRefresh() {
                super.onRefresh();

                ServletWebServerFactory serverFactory = this.getBean(ServletWebServerFactory.class);
                DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);
                // SpringContainer 가 dispatcherServlet 이 applicationContext 가 필요하다는 걸 알고 주입해준다.
                // dispatcherServlet.setApplicationContext(this);

                WebServer webServer = serverFactory.getWebServer(servletContext -> {
                    servletContext.addServlet("dispatcherServlet", dispatcherServlet
                    ).addMapping("/*");
                });

                webServer.start();
            }
        };

        applicationContext.register(applicationClass);
        applicationContext.refresh();
    }


}
