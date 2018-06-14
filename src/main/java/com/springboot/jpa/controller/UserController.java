package com.springboot.jpa.controller;

import com.springboot.jpa.entity.User;
import com.springboot.jpa.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: RoronoaZoro丶WangRUi
 * Time: 2018/6/14/014
 * Describe:
 */
@RestController
@RequestMapping("/user")
@Api("UserController")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("新增")
    @PostMapping("/save")
    public void save(User user){
        userService.save(user);
    }
}
