package com.leo.service.impl;

//import ch.qos.logback.core.joran.util.beans.BeanUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leo.common.Constants;
import com.leo.controller.dto.UserDto;
import com.leo.exception.ServiceException;
import com.leo.mapper.UserMapper;
import com.leo.pojo.User;
import com.leo.service.UserService;
import com.leo.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @version: 1.0
 * @Description: Hello Coder!
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/10/24 15:59
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final Log LOG = Log.get();

    @Override
    public UserDto login(UserDto userDTO) {
//        System.out.println(userDTO+"20202020");
        User one = getUserInfo(userDTO);
        System.out.println(one+"1010101");
        if (one!=null) {
            BeanUtil.copyProperties(one,userDTO,true);//从数据库将数据复制拉到userDTO
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);
            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误111");
        }
//        return one!=null;
    }

    @Override
    public boolean register(UserDto userDTO) {
        User one = getUserInfo(userDTO);
        if (one == null) {
            one = new User();
            String username = userDTO.getUsername();
            String password = userDTO.getPassword();

            one.setName(username);
            one.setPassword(password);
            System.out.println(one);
            save(one);
            return true;
        } else {
            return false;
        }
    }

    private User getUserInfo(UserDto userDto) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("name",userDto.getUsername());
        qw.eq("password",userDto.getPassword());
        User one ;

        try {
            one = getOne(qw);
            System.out.println("***************");
            System.out.println(one);
            System.out.println("***************");
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        return one;
    }
}
