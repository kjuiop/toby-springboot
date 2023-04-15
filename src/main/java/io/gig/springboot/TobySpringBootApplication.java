package io.gig.springboot;

import io.gig.springboot.controller.MainController;
import io.gig.springboot.service.SimpleHelloService;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


/**
 * @author : JAKE
 * @date : 2023/03/12
 */
public class TobySpringBootApplication {

    public static void main(String[] args) {

        // 스프링컨테이너를 대표하는 애플리케이션 컨텍스트,
        GenericWebApplicationContext applicationContext = new GenericWebApplicationContext();
        applicationContext.registerBean(MainController.class);
        applicationContext.registerBean(SimpleHelloService.class);
        applicationContext.refresh();

        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        WebServer webServer = serverFactory.getWebServer(servletContext -> {

            // springServlet 과 연동하기 위함
            // web 요청에 대한 정보를 오브젝트와 연결시키는 정보를 넘겨주지 않았음.
            // 우리가 생성한 helloController 의 Object 를 어떤 url 과 매핑하여 연결시킬 정보를 전달하지 않았음
            // 이걸 web.xml 등으로 전달했었음
            // 매핑 정보를 web.xml 에 적어서 전달하지 않고, 컨트롤러에 명시하는 방법을 선택했다.
            servletContext.addServlet("dispatcherServlet", new DispatcherServlet(applicationContext)
                    ).addMapping("/*");
        });
        webServer.start();
    }
}




