package io.gig.springboot.controller;

import io.gig.springboot.service.HelloService;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * @author : JAKE
 * @date : 2023/03/16
 */
@RequiredArgsConstructor
public class MainController {

    private final HelloService helloService;

    public String healthCheck(String name) {
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
