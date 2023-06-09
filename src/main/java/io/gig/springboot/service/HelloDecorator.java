package io.gig.springboot.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * @author : JAKE
 * @date : 2023/05/03
 */
@Service
@Primary
public class HelloDecorator implements HelloService {

    private final HelloService helloService;

    public HelloDecorator(HelloService helloService) {
        this.helloService = helloService;
    }

    @Override
    public String sayHello(String name) {
        return "#" + helloService.sayHello(name) + "#";
    }
}
