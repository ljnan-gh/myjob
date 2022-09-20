package com.bd.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerCheck {
    String datasourceId() default "";
}
