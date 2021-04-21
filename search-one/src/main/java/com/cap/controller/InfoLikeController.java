package com.cap.controller;


import com.cap.form.EndoreForm;
import com.cap.service.impl.InfoLikeServiceImpl;
import com.cap.util.JWTUtil;
import com.cap.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjx
 * @since 2021-03-05
 */
@RestController
@RequestMapping("/info-like")
public class InfoLikeController {
    @Autowired
    InfoLikeServiceImpl infoLikeService;
    @Autowired
    RequestUtil requestUtil;
    @PostMapping("/change")
    public void change (@RequestBody EndoreForm form, HttpServletRequest request) {
        if (form.getSign() == 1) {
            infoLikeService.endorse(form.getContentId(),requestUtil.getUserId(request));
        } else {
            infoLikeService.cancelEndorse(form.getContentId(),requestUtil.getUserId(request));
        }
    }
}

