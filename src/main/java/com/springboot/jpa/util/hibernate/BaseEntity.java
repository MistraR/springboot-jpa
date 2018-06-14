package com.springboot.jpa.util.hibernate;

import io.swagger.annotations.ApiParam;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author: WangRui
 * Date: 2018/5/20
 * Describe: 实体类基础属性
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity  implements Serializable  {
    private static final long serialVersionUID = -7674269980281525370L;
    @Id
    @GeneratedValue
    @ApiParam("主键ID")
    protected Long id;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @CreatedDate
    @ApiParam("创建时间")
    protected Date createTime;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @LastModifiedDate
    @ApiParam("更新时间")
    protected Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
