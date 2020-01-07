package com.springboot.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springboot.jpa.util.hibernate.BaseEntity;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Author: RoronoaZoro丶WangRUi
 * Time: 2018/6/14/014
 * Describe:
 */
@Data
@Entity
@Table(name = "wm_user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler","fieldHandler"},ignoreUnknown = true)
public class User extends BaseEntity {

    @ApiParam("用户名")
    private String userName;

    @ApiParam("昵称")
    private String nickName;

    @ApiParam("岗位")
    private String position;

    @ApiParam("年龄")
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
