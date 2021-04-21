package com.cap.controller;


import com.cap.dto.InfoContentDTO;
import com.cap.form.InfoContentForm;
import com.cap.pojo.InfoContent;
import com.cap.service.impl.InfoContentServiceImpl;
import com.cap.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjx
 * @since 2021-03-05
 */
@RestController
@RequestMapping("/info-content")
public class InfoContentController {
    @Autowired
    InfoContentServiceImpl infoContentService;
    @Autowired
    RequestUtil requestUtil;
    @PostMapping("/add")
    public void add (@RequestBody InfoContentForm form, HttpServletRequest request) {
        infoContentService.addInfoContent(requestUtil.getUserId(request), form.getInfoId(), form.getContent());
    }
    @PostMapping("/get/{infoId}")
    public List<InfoContentDTO> get (@PathVariable("infoId") String infoId, HttpServletRequest request) {

        return infoContentService.getInfoContent(Long.parseLong(infoId), requestUtil.getUserId(request));
    }
}

