package io.gig.springboot.controller;

import io.gig.springboot.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author : JAKE
 * @date : 2023/05/03
 */
@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("api/hello")
    public String hello(String name) {
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
