package io.gig.springboot;

import io.gig.springboot.controller.MainController;
import io.gig.springboot.service.HelloService;
import io.gig.springboot.service.SimpleHelloService;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


/**
 * @author : JAKE
 * @date : 2023/03/12
 */
@Configuration
public class TobySpringBootApplication {

    // Object 로 구성할 객체를 생성할 때, HelloService 인터페이스를 찾아서 파라미터로 넣어줘
    // 이걸 스프링컨테이너가 역할을 할 수 있도록 등록해줘야함
    @Bean
    public MainController helloController(HelloService helloService) {
        return new MainController(helloService);
    }

    // 구현체를 직접 리턴하네??? 인터페이스 타입으로 리턴해야한다.
    @Bean
    public SimpleHelloService helloService() {
        return new SimpleHelloService();
    }

    public static void main(String[] args) {

        // 스프링컨테이너를 대표하는 애플리케이션 컨텍스트,
        // refresh 는 컨테이너 초기화 기능
        // refresh 가 일어나는 중 부가적으로 어떤 작업이 필요하다면 만든 것이 있음
        // 익명클래스 사용 -> 클래스 뒤에 {} 작성해서 사용
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {

            // 기존에는 외부에서 변수를 생성하여 주입하였으나, 이제는 자기 자신 안에서 사용
            @Override
            protected void onRefresh() {
                super.onRefresh();

                ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
                WebServer webServer = serverFactory.getWebServer(servletContext -> {

                    // springServlet 과 연동하기 위함
                    // web 요청에 대한 정보를 오브젝트와 연결시키는 정보를 넘겨주지 않았음.
                    // 우리가 생성한 helloController 의 Object 를 어떤 url 과 매핑하여 연결시킬 정보를 전달하지 않았음
                    // 이걸 web.xml 등으로 전달했었음
                    // 매핑 정보를 web.xml 에 적어서 전달하지 않고, 컨트롤러에 명시하는 방법을 선택했다.
                    servletContext.addServlet("dispatcherServlet", new DispatcherServlet(this)
                    ).addMapping("/*");

                });
                webServer.start();
            }
        };


        // applicationContext.registerBean(MainController.class);
        // applicationContext.registerBean(SimpleHelloService.class);
        // 처음 GenericWebApplicationContext 에서는 수동으로 Bean 을 모두 등록했었지만 AnnotationConfigWebApplicationContext 를
        // 사용하면, @Configuration 어노테이션이 붙은 클래스에서 @Bean 으로 등록한 팩토리 메서드들을 자동으로 등록해준다.
        applicationContext.register(TobySpringBootApplication.class);

        applicationContext.refresh();
    }
}




