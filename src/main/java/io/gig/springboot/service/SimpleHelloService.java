package io.gig.springboot.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author : JAKE
 * @date : 2023/04/12
 */
@Service
public class SimpleHelloService implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
