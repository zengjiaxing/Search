package com.cap.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cap.pojo.User;
import com.cap.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class RequestUtil {
    @Autowired
    UserServiceImpl usersService;

    public Long getUserId(HttpServletRequest request) {
        String token = request.getHeader("token");
        System.out.println(token);
        try {
            DecodedJWT jwt = JWT.decode(token);
            String username = jwt.getClaim("username").asString();
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username", username);
            return usersService.getOne(wrapper).getUserId();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    public String getUserName(HttpServletRequest request) {
        String token = request.getHeader("token");
        try {
            DecodedJWT jwt = JWT.decode(token);
            String username = jwt.getClaim("username").asString();

            return username;
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
