package com.cap.controller;


import com.baomidou.mybatisplus.extension.api.R;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class TestController {
    @GetMapping("/guest/enter")
    public String login () {
        return "登录成功";
    }
    @GetMapping("/guest/getMessage")
    public String guestGetMessage () {
        return "游客获取信息";
    }
    @GetMapping("/user/getMessage")
    public String userGetMessage () {
        return "用户获取信息";
    }
    @RequiresRoles("user")
    @GetMapping("/admin/getMessage")
    public  String adminGetMessage () {
        return  "管理员获取信息";
    }
    @RequiresRoles(logical = Logical.AND, value = {"user", "admin"})
    @GetMapping("/admin/setMessage")
    public  String adminSetMessage () {
        return  "管理员设置信息";
    }
}
