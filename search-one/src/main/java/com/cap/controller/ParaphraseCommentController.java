package com.cap.controller;


import com.cap.form.ParaphraseCommentForm;
import com.cap.dto.ParaphraseCommentDTO;
import com.cap.service.impl.ParaphraseCommentServiceImpl;
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
@RequestMapping("/paraphrase-comment")
public class ParaphraseCommentController {
    @Autowired
    RequestUtil requestUtil;
    @Autowired
    ParaphraseCommentServiceImpl commentService;

    @PostMapping("/add")
    public void add (@RequestBody ParaphraseCommentForm form, HttpServletRequest request) {
        form.setUserId(requestUtil.getUserId(request));
        commentService.addParaphraseComment(form);
    }
    @GetMapping("/get/{paraphraseId}")
    public List<ParaphraseCommentDTO> get (@PathVariable("paraphraseId") Long paraphraseId, HttpServletRequest request) {
        return commentService.getParaphraseComment(paraphraseId,requestUtil.getUserId(request));
    }
}

