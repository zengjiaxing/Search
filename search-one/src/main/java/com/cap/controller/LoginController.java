package com.cap.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cap.form.UserForm;
import com.cap.mapper.UserMapper;
import com.cap.mapper.UserRoleMapper;
import com.cap.model.ResultMap;
import com.cap.pojo.User;
import com.cap.pojo.UserRole;
import com.cap.util.JWTUtil;

import com.cap.util.ShiroUtil;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

@RestController
public class LoginController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResultMap resultMap;
    @Autowired
    private ShiroUtil shiroUtil;

    @GetMapping("/notLogin")
    public String noLogin() {
        return "你尚未登录";
    }
    @GetMapping("/noRole")
    public String noRole() {
        return "你没有权限";
    }
    @GetMapping("/addUser/{username}/{password}")
    public int addUser(@PathVariable("username") String username, @PathVariable("password") String password) {
        User user = new User();
        user.setPwd(password);
        user.setUsername(username);
        user.setScore(0l);
        try {
            userMapper.insert(user);
        } catch (Exception e) {
            return 0;
        }

        return 1;
    }
    @GetMapping("logout")
    public Integer logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return 1;
    }
    /*
    * 登录
    * */
    @PostMapping("/login")
    public ResultMap login(@RequestBody UserForm user) {
        String realPassword = userMapper.selectOne(new QueryWrapper<User>().eq("username",user.getUsername())).getPwd();
        if (realPassword == null) {
            return resultMap.fail().code(401).token("用户名错误");
        } else if (!realPassword.equals(user.getPassword())) {
            return resultMap.fail().code(401).token("密码错误");
        } else {
            List<Set<String>> listSet = shiroUtil.getPermission(user.getUsername());
            return resultMap.success().code(200).token(JWTUtil.createToken(user.getUsername()))
                    .message("role",listSet.get(0))
                    .message("permission",listSet.get(1));
        }
    }
    @RequestMapping(path = "/unauthorized/{message}")
    public ResultMap unauthorized(@PathVariable String message) throws UnsupportedEncodingException {
        return resultMap.success().code(401).token(message);
    }
}
