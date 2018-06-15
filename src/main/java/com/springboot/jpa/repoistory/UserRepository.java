package com.springboot.jpa.repoistory;

import com.springboot.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Author: RoronoaZoro丶WangRUi
 * Time: 2018/6/14/014
 * Describe:
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findFirstByUserName(String name);

    @Query(value = "from USER where position =?1")
    List<User> findByCustomSqlTest1(String position);

    @Query(value = "select user_name,nick_name,position from wm_user where position = ?1",nativeQuery = true)
    List<User> findByCustomSqlTest2(String position);
}
