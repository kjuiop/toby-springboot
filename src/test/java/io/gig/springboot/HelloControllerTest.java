package io.gig.springboot;

import io.gig.springboot.controller.HelloController;
import io.gig.springboot.controller.MainController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author : JAKE
 * @date : 2023/05/03
 */
public class HelloControllerTest {

    @Test
    void helloController() {
        HelloController helloController = new HelloController(name -> name);

        String ret = helloController.hello("Test");

        Assertions.assertThat(ret).isEqualTo("Test");
    }

    @Test
    void failsHelloController() {
        HelloController helloController = new HelloController(name -> name);

        Assertions.assertThatThrownBy(() -> {
            String ret = helloController.hello(null);
        }).isInstanceOf(NullPointerException.class);

    }
}
