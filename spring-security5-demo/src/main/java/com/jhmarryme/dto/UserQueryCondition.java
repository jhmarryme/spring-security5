package com.jhmarryme.dto;

import lombok.Data;

/**
 * description:
 *
 * @author JiaHao Wang
 * @date 2020/9/8 12:32
 */
@Data
public class UserQueryCondition {

    private String username;

//    @ApiModelProperty("用户年龄起始值")
    private int ageTo;

//    @ApiModelProperty("用户年龄结束值")
    private int age;

}
