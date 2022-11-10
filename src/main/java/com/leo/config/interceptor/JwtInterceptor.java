package com.leo.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.leo.common.Constants;
import com.leo.exception.ServiceException;
import com.leo.pojo.User;
import com.leo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: Hello Coder!
 * @version: 1.0
 * @author: Leo
 * @eamil: fraudLeo1@Gmail.com
 * @date:2022/10/28 17:03
 */

//@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token  = request.getHeader("token");
        //如果不是映射方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        //执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException(Constants.CODE_401,"无token登录,请重新登录");
        }

        // 获取 token中的userid
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new RuntimeException("token验证失败");
        }

        //根据token中的userid查询数据库
        User user = userService.getById(userId);
        if (user==null) {
            throw new ServiceException(Constants.CODE_401,"用户不存在,请重新登录");
        }

        //用户密码加签验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            System.out.println(e);
            throw new RuntimeException("401");
        }

        return true;

    }
}
