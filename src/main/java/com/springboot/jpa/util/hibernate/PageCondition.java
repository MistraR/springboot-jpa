package com.springboot.jpa.util.hibernate;

import io.swagger.annotations.ApiParam;

/**
 * Author: WangRui
 * Date: 2018/5/20
 * Describe: 分页查询的参数
 */
public class PageCondition {
    @ApiParam("页数")
    private int pageNum = 0;

    @ApiParam("每页数量")
    private int pageSize = 15;

    @ApiParam("排序字段")
    private String order;

    @ApiParam("排序规则 默认降序，升序=ASC")
    private String orderBy = "DESC";

    public int getPageNum() {
        return pageNum < 0 ? 0 : pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize == 0 ? 15 : pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
