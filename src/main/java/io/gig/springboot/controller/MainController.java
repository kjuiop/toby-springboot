package io.gig.springboot.controller;

/**
 * @author : JAKE
 * @date : 2023/03/16
 */
public class MainController {

    public String healthCheck(String name) {
        return "Status ok " + name;
    }
}
