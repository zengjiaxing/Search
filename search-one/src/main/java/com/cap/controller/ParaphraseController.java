package com.cap.controller;


import com.cap.form.ChangeParaphraseForm;
import com.cap.form.ParaphraseForm;
import com.cap.pojo.Paraphrase;
import com.cap.service.impl.ParaphraseServiceImpl;
import com.cap.util.RequestUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.Delayed;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjx
 * @since 2021-03-05
 */
@RestController
@RequestMapping("/paraphrase")
public class ParaphraseController {
    @Autowired
    RequestUtil requestUtil;
    @Autowired
    ParaphraseServiceImpl paraphraseService;

    @PostMapping("/add")
    public void add (@RequestBody ParaphraseForm form, HttpServletRequest request) {
        paraphraseService.addParaphrase(form);
    }

    @PostMapping("/change")
    public void change (@RequestBody ChangeParaphraseForm form) {
        paraphraseService.changeParaphrase(form);
    }

    @GetMapping("/get/{infoId}")
    public List<Paraphrase> get (@PathVariable("infoId") Long infoId) {

        return paraphraseService.getParaphraseList(infoId);
    }
}

