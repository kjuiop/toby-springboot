package io.gig.springboot.controller;

import io.gig.springboot.annotation.MyComponent;
import io.gig.springboot.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
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
@RequiredArgsConstructor
@MyComponent
public class MainController {

    private final HelloService helloService;

    @GetMapping
    public String hello(String name) {
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
