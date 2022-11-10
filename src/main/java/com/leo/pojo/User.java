package com.leo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Description: Hello Coder!
 * @version: 1.0
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/10/18 18:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
@ToString
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    @JsonIgnore
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String address;
//    @TableField("avatar_url")//这里不需要这个注解,因为spring?mybatis框架自动将这里的下划线转换成驼峰了
//    private String avatarUrl;
    private String role;

}
