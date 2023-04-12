package io.gig.springboot.service;

/**
 * @author : JAKE
 * @date : 2023/04/12
 */
public class SimpleHelloService implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
