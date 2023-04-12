package io.gig.springboot;

import io.gig.springboot.controller.MainController;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.support.GenericApplicationContext;
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

        // 스프링컨테이너를 대표하는 애플리케이션 컨텍스트,
        // 리소스 접근, 내부 이벤트 전달, 구독하는 방법 등을 수행함.
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        // object 가 아닌 클래스 정보만 넘김
        applicationContext.registerBean(MainController.class);
        // bean object 를 생성함.
        applicationContext.refresh();


        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        WebServer webServer = serverFactory.getWebServer(servletContext -> {

            // Spring Container 에서 직접 mainController 를 다루지 않음
//            MainController mainController = new MainController();


            servletContext.addServlet("frontcontroller", new HttpServlet() {
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

                    // 인증, 보안, 다국어 처리 등 각종 공통 기능이 적용됨
                    if (req.getRequestURI().equals("/health-check") && req.getMethod().equals(HttpMethod.GET.name())) {
                        String name = req.getParameter("name");

                        MainController mainController = applicationContext.getBean(MainController.class);
                        String result = mainController.healthCheck(name);
                        // 직접 클래스를 의존하지 않고, 등록된 빈을 가져오는 방식으로 진행됨.
                        // String result = mainController.healthCheck(name);

                        // 컨테이너에서 성공으로 리턴함.
                        // resp.setStatus(HttpStatus.OK.value());

                        resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
                        // resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);

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


