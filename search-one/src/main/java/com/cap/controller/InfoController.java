package com.cap.controller;


import com.cap.form.InfoDataForm;
import com.cap.pojo.User;
import com.cap.service.impl.SearchServiceImpl;
import com.cap.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/info")
public class InfoController {
    @Autowired
    SearchServiceImpl searchService;
    @Autowired
    RequestUtil requestUtil;

    @PostMapping("/add")
    public void add (@RequestBody InfoDataForm form, HttpServletRequest request) {
        searchService.insertNewSearchData(form,requestUtil.getUserId(request));
    }
}

