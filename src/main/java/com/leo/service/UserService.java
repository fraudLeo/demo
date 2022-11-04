package com.leo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leo.controller.dto.UserDto;
import com.leo.mapper.UserMapper;
import com.leo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: Hello Coder!
 * @version: 1.0
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/10/18 20:31
 */

@Service
public interface UserService extends IService<User> {
    public UserDto login(UserDto user);

    boolean register(UserDto userDTO);


//    @Override
//    public boolean save(User user) {
////        if (user.getId() ==null) {
////            return save(user);
////        } else {
////            return updateById(user);
////        }
//        return saveOrUpdate(user);
//    }
}
