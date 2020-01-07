package com.springboot.jpa.service;

import com.springboot.jpa.entity.User;
import com.springboot.jpa.util.hibernate.PageCondition;
import com.springboot.jpa.util.hibernate.Pager;
import com.springboot.jpa.vo.UserQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * Author: RoronoaZoro丶WangRUi
 * Time: 2018/6/14/014
 * Describe:
 */
public interface UserService {

    void save(User user);

    User getOne(Long id);

    void delete(Long id);

    Page<User> getPage(Pageable pageable,UserQueryVo userQueryVo);

    Pager<User> getPager(PageCondition condition, UserQueryVo userQueryVo);

    List<User> findByCustomSqlTest1(UserQueryVo userQueryVo);

    List<User> findByCustomSqlTest2(UserQueryVo userQueryVo);

    User jpaName(UserQueryVo userQueryVo);

    Page<User> jpaPageSelect(PageCondition condition,UserQueryVo userQueryVo);

    Page<User> jpaSpecificationTest1(PageCondition condition,UserQueryVo userQueryVo);

    Page<User> jpaSpecificationTest2(PageCondition condition,UserQueryVo userQueryVo);

    List<User> getRelation();

    Page<User> getRelatePage();
}
