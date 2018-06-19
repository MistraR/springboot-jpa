package com.springboot.jpa.util.hibernate.dao;

import com.springboot.jpa.util.hibernate.Pager;
import com.springboot.jpa.util.hibernate.QueryHelper;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/5/20
 * Describe: JPA+Hibernate 自定义sql查询工具类
 */
@Repository
public class CommonDAOImpl implements CommonDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;


    @Override
    public <W> Pager<W> findPager(QueryHelper helper) {
        Query contentQuery;
        Query countQuery;
        //如果使用原生SQL
        if (helper.isUseNativeSql()) {
            contentQuery = entityManager.createNativeQuery(helper.getContentQueryString());
            countQuery = entityManager.createNativeQuery(helper.getCountQueryString());
            contentQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        } else {
            contentQuery = entityManager.createQuery(helper.getContentQueryString());
            countQuery = entityManager.createQuery(helper.getCountQueryString());
        }
        for (int i = 0; i < helper.getParameters().size(); i++) {
            Object p = helper.getParameters().get(i);
            contentQuery.setParameter(i + 1, p);
            countQuery.setParameter(i + 1, p);
        }
        contentQuery.setFirstResult(helper.getCurrentPage() * helper.getPageSize());
        contentQuery.setMaxResults(helper.getPageSize());
        List list = contentQuery.getResultList();
        int count = 0;
        if (helper.isUseNativeSql()) {
            BigInteger v = (BigInteger) countQuery.getSingleResult();
            count = v.intValue();
        } else {
            Long v = (Long) countQuery.getSingleResult();
            count = v.intValue();
        }
        Pager pager = new Pager(helper.getCurrentPage(), helper.getPageSize(), count, list);
        return pager;
    }

    @Override
    public <W> Pager<W> findPager(QueryHelper helper, Class<W> tClass) {
        if (!helper.isUseNativeSql()) {
            return findPager(helper);
        } else {
            Query countQuery;
            countQuery = entityManager.createNativeQuery(helper.getCountQueryString());
            for (int i = 0; i < helper.getParameters().size(); i++) {
                Object p = helper.getParameters().get(i);
                countQuery.setParameter(i, p);
            }
            BigInteger v = (BigInteger) countQuery.getSingleResult();
            String sql = helper.getContentQueryString() + " LIMIT ?, ?";
            helper.getParameters().add(helper.getCurrentPage() * helper.getPageSize());
            helper.getParameters().add(helper.getPageSize());
            List<W> resultList = execSql(sql, helper.getParameters(), tClass);
            Pager pager = new Pager(helper.getCurrentPage(), helper.getPageSize(), v.intValue(), resultList);
            return pager;
        }
    }

    @Override
    public <W> List<W> findList(QueryHelper helper) {
        Query contentQuery;
        //如果使用原生SQL
        if (helper.isUseNativeSql()) {
            contentQuery = entityManager.createNativeQuery(helper.getContentQueryString());
            contentQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        } else {
            contentQuery = entityManager.createQuery(helper.getContentQueryString());
        }
        for (int i = 0; i < helper.getParameters().size(); i++) {
            Object p = helper.getParameters().get(i);
            contentQuery.setParameter(i + 1, p);
        }
        return contentQuery.getResultList();
    }

    @Override
    public <W> List<W> findList(QueryHelper helper, Class<W> tClass) {
        if (!helper.isUseNativeSql()) {
            return findList(helper);
        } else {
            return execSql(helper.getContentQueryString(), helper.getParameters(), tClass);
        }
    }

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param params 参数
     * @param clazz  转换的对象
     * @param <W>
     * @return
     */
    private <W> List<W> execSql(String sql, List<Object> params, Class<W> clazz) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            rs = ps.executeQuery();
            return convertToEntity(rs, clazz);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将结果集转换成对象
     *
     * @param rs
     * @param clazz
     * @param <W>
     * @return
     */
    private <W> List<W> convertToEntity(ResultSet rs, Class<W> clazz) {
        List<W> resultList = new ArrayList<>();
        try {
            //结果集 中列的名称和类型的信息
            ResultSetMetaData rsm = rs.getMetaData();
            int colNumber = rsm.getColumnCount();
            //getDeclaredFields()获得某个类的所有字段,public和protected和private,但是不包括父类字段，getFields()获得某个类的所有的公共public的字段，包括父类中的字段。
            List<Field> fieldList = new ArrayList<>();
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            fieldList.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
            Field[] fields = new Field[fieldList.size()];
            fieldList.toArray(fields);
            while (rs.next()) {
                W entity = clazz.newInstance();
                for (int i = 1; i <= colNumber; i++) {
                    Object value = rs.getObject(i);
                    //匹配实体类中对应的属性
                    for (int j = 0; j < fields.length; j++) {
                        Field f = fields[j];
                        if (f.getName().equals(rsm.getColumnName(i))) {
                            boolean flag = f.isAccessible();
                            if (f.getType() == Integer.class || f.getType() == int.class) {
                                value = rs.getInt(i);
                            }
                            f.setAccessible(true);
                            f.set(entity, value);
                            f.setAccessible(flag);
                            break;
                        }
                    }

                }
                resultList.add(entity);
            }
        } catch (InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
