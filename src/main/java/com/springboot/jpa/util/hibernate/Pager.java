package com.springboot.jpa.util.hibernate;

import java.util.List;

/**
 * Author: WangRui
 * Date: 2018/5/20
 * Describe:
 */
public class Pager<W> {
    /**
     * 当前页 从0开始
     */
    private Integer pageNumber;
    /**
     * 每页数量
     */
    private Integer pageSize;
    private Integer totalPages;
    private Integer totalElements;
    private List<W> content;

    public Pager(int pageNumber, int pageSize, int totalElements, List<W> content) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.content = content;
        if (totalElements <= 0) {
            this.totalPages = 1;
        } else {
            this.totalPages = (int) Math.ceil((double) totalElements / (double) pageSize);
        }
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public List<W> getContent() {
        return content;
    }

    public void setContent(List<W> content) {
        this.content = content;
    }
}
