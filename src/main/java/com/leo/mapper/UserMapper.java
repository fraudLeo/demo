package com.leo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leo.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Description: Hello Coder!
 * @version: 1.0
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/10/18 18:46
 */
@Mapper
public interface UserMapper extends BaseMapper<User>{
//    @Select("select * from sys_user")
//    List<User> findAll();
//
//    @Insert("insert into sys_user(name, password, nickname, email, phone, address) values (#{name}, #{password}, #{nickname},#{email}, #{phone}, #{address})")
//    int insert(User user);
//
//    int update(User user);
//
//    @Delete("delete from sys_user where id=#{id}")
//    Integer deleteById(@Param("id") Integer id);

//    @Select("select * from sys_user where name like concat('%',#{name},'%') and email like concat('%',#{email},'%') and address like concat('%',#{address},'%') limit #{pageNum},#{pageSize}")
//    List<User> selectPage(Integer pageNum, Integer pageSize, String name, String email, String address);
//
//    @Select("select count(*) from sys_user where name like concat('%',#{name},'%') and email like concat('%',#{email},'%') and address like concat('%',#{address},'%')")
//    Integer selectTotal(String name, String email, String address);
}
