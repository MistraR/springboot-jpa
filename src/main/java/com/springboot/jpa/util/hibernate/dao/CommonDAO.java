package com.springboot.jpa.util.hibernate.dao;

import com.springboot.jpa.util.hibernate.Pager;
import com.springboot.jpa.util.hibernate.QueryHelper;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;


/**
 * Author : WangRui
 * Date : 2018/5/20
 * Describe: JPA+Hibernate 自定义sql查询工具类
 */
@EnableJpaRepositories
public interface CommonDAO {
    /**
     * 分页方法
     *
     * @param helper
     * @return
     */
    <W> Pager<W> findPager(QueryHelper helper);

    /**
     * 分页查询 返回指定的对象
     *
     * @param helper
     * @param tClass
     * @param <W>
     * @return
     */
    <W> Pager<W> findPager(QueryHelper helper, Class<W> tClass);

    /**
     * 列表查询
     *
     * @param helper
     * @return
     */
    <W> List<W> findList(QueryHelper helper);

    /**
     * 列表查询 返回指定对象
     *
     * @param helper
     * @param tClass
     * @param <W>
     * @return
     */
    <W> List<W> findList(QueryHelper helper, Class<W> tClass);
}
