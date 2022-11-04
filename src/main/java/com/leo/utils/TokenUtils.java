package com.leo.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.leo.pojo.User;
import com.leo.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Description: 生成token
 * @version: 1.0
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/10/28 15:29
 */
//token 有三层,头部.载荷.签证
@Component
public class TokenUtils {


    private static UserService staticUserService;
    @Resource
    private UserService userService;

    @PostConstruct
    public void setUserService() {
        System.out.println(userService   );
        staticUserService = userService;
    }

    public static String genToken(String userId,String sign) {
        return JWT.create().withAudience(userId)// 将userid保存到token里面 作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2)) //120分钟后过期
                .sign(Algorithm.HMAC256(sign));// 以password作为token的密钥
    }

    //获取当前用户信息
    public static User getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        if (StrUtil.isNotBlank(token)) {
           try{
               String userId = JWT.decode(token).getAudience().get(0);
               return staticUserService.getById(Integer.valueOf(userId));
           } catch (Exception e) {
               return null;
           }

        }
        return null;
    }
}
