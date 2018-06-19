package com.springboot.jpa.service.impl;

import com.springboot.jpa.entity.User;
import com.springboot.jpa.repoistory.UserRepository;
import com.springboot.jpa.service.UserService;
import com.springboot.jpa.util.hibernate.PageCondition;
import com.springboot.jpa.util.hibernate.Pager;
import com.springboot.jpa.util.hibernate.QueryHelper;
import com.springboot.jpa.util.hibernate.dao.CommonDAO;
import com.springboot.jpa.vo.UserQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Author: RoronoaZoro丶WangRUi
 * Time: 2018/6/14/014
 * Describe:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommonDAO commonDAO;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User getOne(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<User> getPage(Pageable pageable, UserQueryVo userQueryVo) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Pager<User> getPager(PageCondition condition, UserQueryVo userQueryVo) {
        /*QueryHelper helper = new QueryHelper(User.class)
                .addCondition(StringUtils.isNoneBlank(userQueryVo.getUserName()), "userName like ", "%" + userQueryVo.getUserName() + "%")
                .addCondition(StringUtils.isNoneBlank(userQueryVo.getNickName()), "nickName like ", "%" + userQueryVo.getNickName() + "%")
                .addCondition(StringUtils.isNoneBlank(userQueryVo.getPosition()), "position = ?", userQueryVo.getPosition())
                .setPageCondition(condition)
                .useNativeSql(false);*/
        QueryHelper helper = new QueryHelper("id,user_name as userName,nick_name as nickName,position,age"," wm_user")
                .setPageCondition(condition)
                .useNativeSql(true);
        Pager<User> pager = commonDAO.findPager(helper,User.class);
        return pager;
    }

    @Override
    public List<User> findByCustomSqlTest1(UserQueryVo userQueryVo) {
        return userRepository.selectByCustomSqlTest1(userQueryVo.getPosition());
    }

    @Override
    public List<User> findByCustomSqlTest2(UserQueryVo userQueryVo) {
        return userRepository.selectByCustomSqlTest2(userQueryVo.getPosition());
    }

    @Override
    public User jpaName(UserQueryVo userQueryVo) {
        return userRepository.findFirstByUserName(userQueryVo.getUserName());
    }

    /**
     * Jpa自带的分页,排序和条件查询测试
     *
     * @param condition
     * @param userQueryVo
     * @return
     */
    @Override
    public Page<User> jpaPageSelect(PageCondition condition, UserQueryVo userQueryVo) {
        PageRequest pageRequest = PageRequest.of(condition.getPageNum(), condition.getPageSize(), Sort.Direction.ASC, condition.getOrder());
        Page<User> users = userRepository.findByNickName(userQueryVo.getNickName(), pageRequest);
        return users;
    }

    /**
     * JpaSpecificationExecutor条件查询接口测试1
     *
     * @param condition
     * @param userQueryVo
     * @return
     */
    @Override
    public Page<User> jpaSpecificationTest1(PageCondition condition, UserQueryVo userQueryVo) {
        PageRequest pageRequest = PageRequest.of(condition.getPageNum(), condition.getPageSize(), Sort.Direction.DESC, condition.getOrder());
        Page<User> page = userRepository.findAll(new Specification<User>() {
            @Override
            public javax.persistence.criteria.Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<String> userName = root.get("userName");
                Path<String> position = root.get("position");
                //这里可以设置任意条查询条件
                query.where(cb.like(userName, "%" + userQueryVo.getUserName() + "%"), cb.like(position, "%" + userQueryVo.getPosition() + "%"));
                //这种方式使用JPA的API设置了查询条件，所以不需要再返回查询条件Predicate给Spring Data Jpa，故最后return null;即可。
                return null;
            }
        }, pageRequest);
        return page;
    }

    /**
     * JpaSpecificationExecutor条件查询接口测试2
     *
     * @param condition
     * @param userQueryVo
     * @return
     */
    @Override
    public Page<User> jpaSpecificationTest2(PageCondition condition, UserQueryVo userQueryVo) {
        PageRequest pageRequest = PageRequest.of(condition.getPageNum(), condition.getPageSize(), Sort.Direction.DESC, condition.getOrder());
        Page<User> page = userRepository.findAll(new Specification<User>() {
            @Override
            public javax.persistence.criteria.Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate1 = cb.like(root.get("userName"),"%" + userQueryVo.getUserName() + "%");
                Predicate predicate2 = cb.like(root.get("position"),"%" + userQueryVo.getPosition() + "%");
                query.where(cb.or(predicate1,predicate2));
                //等于:query.where(cb.or(cb.like(root.get("userName"),"%" + userQueryVo.getUserName() + "%"),cb.like(root.get("position"),"%" + userQueryVo.getPosition() + "%")));
                return null;
            }
        }, pageRequest);
        return page;
    }
}
