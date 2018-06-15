package com.springboot.jpa.controller;

import com.springboot.jpa.entity.User;
import com.springboot.jpa.service.UserService;
import com.springboot.jpa.util.hibernate.PageCondition;
import com.springboot.jpa.util.hibernate.Pager;
import com.springboot.jpa.util.web.annotation.*;
import com.springboot.jpa.vo.UserQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @AddUrl
    public void save(User user){
        userService.save(user);
    }

    @ApiOperation("查询单条")
    @GetOneUrl
    public User getOne(Long id){
        return userService.getOne(id);
    }

    @ApiOperation("删除")
    @DeleteUrl
    public void delete(Long id){
        userService.delete(id);
    }

    @ApiOperation("分页查询1-自定义的查询辅助类")
    @SelectPageUrl
    public Pager<User> getPager(PageCondition condition, UserQueryVo userQueryVo){
        return userService.getPager(condition,userQueryVo);
    }

    @ApiOperation("分页查询2-Jpa自带的查询辅助类")
    @PageUrl
    public Page<User> getPage(Pageable pageable,UserQueryVo userQueryVo){
        return userService.getPage(pageable,userQueryVo);
    }

    @ApiOperation("自定义sql测试1")
    @GetMapping("/customSql1")
    public List<User> customSql1(UserQueryVo userQueryVo){
        return userService.findByCustomSqlTest1(userQueryVo);
    }

    @ApiOperation("自定义sql测试2")
    @GetMapping("/customSql2")
    public List<User> customSql2(UserQueryVo userQueryVo){
        return userService.findByCustomSqlTest2(userQueryVo);
    }

    @ApiOperation("Jpa命名规范接口测试")
    @GetMapping("/jpaName")
    public User jpaName(UserQueryVo userQueryVo){
        return userService.jpaName(userQueryVo);
    }
}
