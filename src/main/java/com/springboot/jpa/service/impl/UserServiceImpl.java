package com.springboot.jpa.service.impl;

import com.springboot.jpa.entity.User;
import com.springboot.jpa.repoistory.UserRepoistory;
import com.springboot.jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: RoronoaZoro丶WangRUi
 * Time: 2018/6/14/014
 * Describe:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepoistory userRepoistory;

    @Override
    public void save(User user) {
        userRepoistory.save(user);
    }
}
