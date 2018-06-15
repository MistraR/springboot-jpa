package com.springboot.jpa.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * Author: RoronoaZoro丶WangRUi
 * Time: 2018/6/15/015
 * Describe:
 */
@Data
public class UserQueryVo {

    @ApiParam("用户名")
    private String userName;

    @ApiParam("昵称")
    private String nickName;

    @ApiParam("岗位")
    private String position;
}
