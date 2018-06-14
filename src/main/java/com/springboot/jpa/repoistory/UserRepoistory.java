package com.springboot.jpa.repoistory;

import com.springboot.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: RoronoaZoro丶WangRUi
 * Time: 2018/6/14/014
 * Describe:
 */
public interface UserRepoistory extends JpaRepository<User,Long> {
}
