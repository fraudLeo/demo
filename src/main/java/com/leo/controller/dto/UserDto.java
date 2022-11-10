package com.leo.controller.dto;

import com.leo.pojo.Menu;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Description: 接受前端登录请求参数
 * @version: 1.0
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/10/24 12:12
 */
@Data
@ToString
public class UserDto {
    private String username;
    private String password;
    private String nickname;
    private String token;
    private String role;
    private List<Menu> menus;


}
