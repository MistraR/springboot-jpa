package com.springboot.jpa.util.hibernate;

import io.swagger.annotations.ApiParam;
import lombok.Data;
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
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity  implements Serializable  {
    private static final long serialVersionUID = -7674269980281525370L;
    @Id
    @GeneratedValue
    @ApiParam("主键ID")
    protected Long id;

    @CreatedDate
    @ApiParam("创建时间")
    protected Date createTime;

    @LastModifiedDate
    @ApiParam("更新时间")
    protected Date modifyTime;

}
