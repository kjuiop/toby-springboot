package io.gig.springboot.controller;

import io.gig.springboot.service.SimpleHelloService;

import java.util.Objects;

/**
 * @author : JAKE
 * @date : 2023/03/16
 */
public class MainController {

    public String healthCheck(String name) {
        SimpleHelloService helloService = new SimpleHelloService();
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
