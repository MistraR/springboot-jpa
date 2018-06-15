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

    @Query(value = "from User where position =?1")
    List<User> selectByCustomSqlTest1(String position);

    @Query(value = "select * from wm_user where position = ?1",nativeQuery = true)
    List<User> selectByCustomSqlTest2(String position);
}
