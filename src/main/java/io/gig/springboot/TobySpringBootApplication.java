package io.gig.springboot;

import io.gig.springboot.controller.MainController;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author : JAKE
 * @date : 2023/03/12
 */
public class TobySpringBootApplication {

    public static void main(String[] args) {
        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        WebServer webServer = serverFactory.getWebServer(servletContext -> {

            MainController mainController = new MainController();


            servletContext.addServlet("frontcontroller", new HttpServlet() {
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

                    // 인증, 보안, 다국어 처리 등 각종 공통 기능이 적용됨
                    if (req.getRequestURI().equals("/health-check") && req.getMethod().equals(HttpMethod.GET.name())) {
                        String name = req.getParameter("name");

                        String result = mainController.healthCheck(name);

                        resp.setStatus(HttpStatus.OK.value());
                        resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
                        resp.getWriter().print(result);
                    }
                    else if (req.getRequestURI().equals("/users")) {
                        resp.setStatus(HttpStatus.NOT_FOUND.value());
                    }
                    else {
                        resp.setStatus(HttpStatus.NOT_FOUND.value());
                    }

                }
            }).addMapping("/*");
        });
        webServer.start();
    }
}


