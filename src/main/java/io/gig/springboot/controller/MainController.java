package io.gig.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : JAKE
 * @date : 2023/03/16
 */
@RestController
public class MainController {

    @GetMapping("health-check")
    public String healthCheck() {
        return "ok";
    }
}
