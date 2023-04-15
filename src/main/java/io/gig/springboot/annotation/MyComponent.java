package io.gig.springboot.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : JAKE
 * @date : 2023/04/15
 */
// 언제까지 살아있을 것인가.
@Retention(RetentionPolicy.RUNTIME)
// 애노테이션이 적용될 유형
@Target(ElementType.TYPE)
@Component
public @interface MyComponent {
}
