package com.springboot.jpa.entity;

import com.springboot.jpa.util.hibernate.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: WangRui
 * @Version: 1.0
 * @Time: 2020/1/7 22:26
 * @Description:
 */
@Data
@Entity
@Table(name = "wm_role")
public class Role extends BaseEntity {

    private String roleName;
}
