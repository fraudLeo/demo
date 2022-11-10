package com.leo.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.leo.mapper.Role_Menu_Mapper;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: Hello Coder!
 * @version: 1.0
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/11/8 20:40
 */
@TableName("sys_role_menu")
@Data
public class Role_Menu {


    private Integer roleId;
    private Integer menuId;

}

