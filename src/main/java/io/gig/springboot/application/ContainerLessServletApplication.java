package io.gig.springboot.application;

/**
 * @author : JAKE
 * @date : 2023/04/15
 */

import io.gig.springboot.controller.MainController;
import io.gig.springboot.service.SimpleHelloService;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * frontcontroller servlet 직접 구현 형태
 *
 * // 스프링컨테이너를 대표하는 애플리케이션 컨텍스트,
 * // 리소스 접근, 내부 이벤트 전달, 구독하는 방법 등을 수행함.
 * GenericApplicationContext applicationContext = new GenericApplicationContext();
 * // object 가 아닌 클래스 정보만 넘김
 * applicationContext.registerBean(MainController.class);
 * // DI 주입은 어떻게 할까? , 빈을 등록하면 알아서 적용됨.
 * applicationContext.registerBean(SimpleHelloService.class);
 * // bean object 를 생성함.
 * applicationContext.refresh();
 *
 *

 */

public class ContainerLessServletApplication {

    public static void main(String[] args) {

        // 스프링컨테이너를 대표하는 애플리케이션 컨텍스트,
        // 리소스 접근, 내부 이벤트 전달, 구독하는 방법 등을 수행함.
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        // object 가 아닌 클래스 정보만 넘김
        applicationContext.registerBean(MainController.class);
        // DI 주입은 어떻게 할까? , 빈을 등록하면 알아서 적용됨.
        applicationContext.registerBean(SimpleHelloService.class);
        // bean object 를 생성함.
        applicationContext.refresh();


        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        WebServer webServer = serverFactory.getWebServer(servletContext -> {

            // Spring Container 에서 직접 mainController 를 다루지 않음
            // MainController mainController = new MainController();


            servletContext.addServlet("frontcontroller", new HttpServlet() {
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

                    // 인증, 보안, 다국어 처리 등 각종 공통 기능이 적용됨
                    if (req.getRequestURI().equals("/health-check") && req.getMethod().equals(HttpMethod.GET.name())) {
                        String name = req.getParameter("name");

                        MainController mainController = applicationContext.getBean(MainController.class);
                        String result = mainController.hello(name);
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
