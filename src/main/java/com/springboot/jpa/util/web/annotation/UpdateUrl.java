package com.springboot.jpa.util.web.annotation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * Author: WangRui
 * Date: 2018/5/20
 * Describe: 单条更新方法RequestMapping注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(value = "/update", method = RequestMethod.POST)
public @interface UpdateUrl {
}
