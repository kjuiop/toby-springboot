package io.gig.springboot.controller;

import io.gig.springboot.annotation.MyComponent;
import io.gig.springboot.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author : JAKE
 * @date : 2023/03/16
 */
@RestController
@RequestMapping("hello")
@MyComponent
public class MainController {

    private final HelloService helloService;

    // final 로 선언된 객체는 생성자 모두 생성된 이후에 관리하는데, applicationContext 는 그 이전에 생성되서 주입된 것이기 때문에 final 이 안됨
    private final ApplicationContext applicationContext;

    public MainController(HelloService helloService, ApplicationContext applicationContext) {
        this.helloService = helloService;
        this.applicationContext = applicationContext;

        System.out.println(applicationContext);
    }


    @GetMapping
    public String hello(String name) {
        return helloService.sayHello(Objects.requireNonNull(name));
    }

}
