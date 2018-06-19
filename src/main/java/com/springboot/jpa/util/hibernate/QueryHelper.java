package com.springboot.jpa.util.hibernate;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: WangRui
 * Date: 2018/5/20
 * Describe:
 */
public class QueryHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryHelper.class);
    private static String ALIAS = "w";
    private StringBuffer fromClause = new StringBuffer();
    private StringBuffer whereClause = new StringBuffer();
    private StringBuffer orderByClause = new StringBuffer();
    private String sql;
    /**
     * 查询结果
     */
    private String resultMap;
    private Integer currentPage;
    private Integer pageSize;
    /**
     * 参数
     */
    private List<Object> parameters = new ArrayList<>();
    /**
     * 是否使用原生SQL
     */
    private boolean useNativeSql = true;

    /**
     * @param resultMap 结果集
     * @param table     表+别名  比如 user u
     */
    public QueryHelper(String resultMap, String table) {
        this.resultMap = resultMap;
        fromClause.append(" FROM " + table + " ");
    }

    /**
     * @param clazz
     * @param alias
     */
    public QueryHelper(Class clazz, String alias) {
        ALIAS = alias;
        fromClause.append(" FROM ").append(clazz.getSimpleName()).append(" ").append(ALIAS);
    }

    public QueryHelper(Class clazz) {
        fromClause.append(" FROM ").append(clazz.getSimpleName()).append(" ").append(ALIAS);
    }

    public QueryHelper addJoin(String condition, Object... params) {
        fromClause.append(" " + condition + " ");
        // 参数
        if (params != null) {
            for (Object p : params) {
                parameters.add(p);
            }
        }
        return this;
    }

    public QueryHelper useNativeSql(boolean useNativeSql) {
        this.useNativeSql = useNativeSql;
        return this;
    }

    public QueryHelper setPageCondition(PageCondition pageCondition) {
        setPaging(pageCondition.getPageNum(), pageCondition.getPageSize());
        addOrderProperty(StringUtils.isNotBlank(pageCondition.getOrder()), pageCondition.getOrder(), "DESC".equals(pageCondition.getOrderBy()));
        return this;
    }

    /**
     * 设置分页信息
     *
     * @return
     */
    public QueryHelper setPaging(int currentPage, int pageSize) {
        this.currentPage = currentPage < 0 ? 0 : currentPage;
        this.pageSize = pageSize;
        return this;
    }

    /**
     * 拼接where语句
     *
     * @param condition
     * @param params
     * @return
     */
    public QueryHelper addCondition(String condition, Object... params) {
        if (whereClause.length() == 0) {
            whereClause.append(" WHERE ").append(condition);
        } else {
            whereClause.append(" AND ").append(condition);
        }
        if (params != null) {
            for (Object p : params) {
                parameters.add(p);
            }
        }
        return this;
    }

    /**
     * 如果第一个参数为true，则拼接Where子句
     *
     * @param append
     * @param condition
     * @param params
     */
    public QueryHelper addCondition(boolean append, String condition, Object... params) {
        if (append) {
            addCondition(condition, params);
        }
        return this;
    }

    /**
     * 拼接where子句   以or的形式
     */
    public QueryHelper addOrCondition(String condition, Object... params) {
        // 拼接
        if (whereClause.length() == 0) {
            whereClause.append(" WHERE ").append(condition);
        } else {
            whereClause.append(" OR ").append(condition);
        }
        // 参数
        if (params != null) {
            for (Object p : params) {
                parameters.add(p);
            }
        }

        return this;
    }

    /**
     * 如果第一个参数为true，则拼接Where子句   or形式
     *
     * @param append
     * @param condition
     * @param params
     */
    public QueryHelper addOrCondition(boolean append, String condition, Object... params) {
        if (append) {
            addOrCondition(condition, params);
        }
        return this;
    }


    /**
     * 拼接OrderBy子句
     *
     * @param propertyName 参与排序的属性名
     * @param asc          true表示升序，false表示降序
     */
    public QueryHelper addOrderProperty(String propertyName, boolean asc) {
        if (orderByClause.length() == 0) {
            orderByClause.append(" ORDER BY ").append(propertyName).append((asc ? " ASC" : " DESC"));
        } else {
            orderByClause.append(", ").append(propertyName).append((asc ? " ASC" : " DESC"));
        }
        return this;
    }

    /**
     * 如果第一个参数为true，则拼接OrderBy子句
     *
     * @param append
     * @param propertyName
     * @param asc
     */
    public QueryHelper addOrderProperty(boolean append, String propertyName, boolean asc) {
        if (append) {
            addOrderProperty(propertyName, asc);
        }
        return this;
    }

    /**
     * 拼接groupby
     *
     * @param fields
     * @return
     */
    public QueryHelper addGroupBy(String fields) {
        whereClause.append(" GROUP BY ").append(fields);
        return this;
    }

    /**
     * 获取列表的sql/hql
     *
     * @return
     */
    public String getContentQueryString() {
        String execSql;
        if (StringUtils.isNotBlank(sql)) {
            execSql = "SELECT w.* FROM (" + sql + ") w";
        } else {
            if (isUseNativeSql()) {
                execSql = "SELECT w.* FROM ("
                        + "SELECT "
                        + resultMap
                        + fromClause.toString()
                        + whereClause.toString()
                        + orderByClause.toString()
                        + ") w";
            } else {
                execSql = "SELECT " + ALIAS + " " + fromClause.toString()
                        + whereClause.toString()
                        + orderByClause.toString();
            }

        }

        LOGGER.info("拼接完成的Sql语句：" + execSql);
        return execSql;
    }

    /**
     * 获取计算总和的sql/hql
     *
     * @return
     */
    public String getCountQueryString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT COUNT(1) ");
        if (StringUtils.isNotBlank(sql)) {
            buffer.append("FROM (" + sql + ") w ");
        } else {
            buffer.append(fromClause.toString()).append(whereClause.toString());
        }
        LOGGER.info("查询总数的Sql语句：", buffer.toString());
        return buffer.toString();
    }

    public QueryHelper setSql(String sql) {
        this.sql = sql;
        return this;
    }

    public boolean isUseNativeSql() {
        return useNativeSql;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }
}
