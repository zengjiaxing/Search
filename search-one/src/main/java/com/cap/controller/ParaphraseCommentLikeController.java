package com.cap.controller;


import com.cap.form.ParaphraseCommentLikeForm;
import com.cap.service.impl.ParaphraseCommentLikeServiceImpl;
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
@RequestMapping("/paraphrase-comment-like")
public class ParaphraseCommentLikeController {
    @Autowired
    RequestUtil requestUtil;
    @Autowired
    ParaphraseCommentLikeServiceImpl paraphraseCommentLikeService;

    @PostMapping("/change")
    public void change (@RequestBody ParaphraseCommentLikeForm form, HttpServletRequest request) {
        form.setUserId(requestUtil.getUserId(request));
        if (form.getSign() == 1) {
            paraphraseCommentLikeService.like(form);
        } else {
            paraphraseCommentLikeService.cancelLike(form);
        }
    }

}

