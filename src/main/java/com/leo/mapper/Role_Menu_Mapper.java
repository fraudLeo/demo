package com.leo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leo.pojo.Role_Menu;
import lombok.Data;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Description: Hello Coder!
 * @version: 1.0
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/11/8 20:41
 */
@Mapper
public interface Role_Menu_Mapper extends BaseMapper<Role_Menu> {


    Integer roleId = null;
     List<Integer> menuId = null;


    //返回更改次数
    @Delete("delete from sys_role_menu where role_id = #{roleId}")
    int deleteByRoleId(@Param("roleId")Integer roleId);
//    @Insert("insert into sys_role_menu (role_id,menu_id) values(#{role_menu.roleId},#{#role_menu.menuId })")
//    int insert(Role_Menu role_menu);

    @Select("select menu_id from sys_role_menu where role_id = #{roleId}")
    List<Integer> selectByRoleId(@Param("roleId") Integer roleId);
}
