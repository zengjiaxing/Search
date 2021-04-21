package com.cap.service.impl;

import com.cap.form.ParaphraseCommentForm;
import com.cap.pojo.ParaphraseComment;
import com.cap.mapper.ParaphraseCommentMapper;
import com.cap.dto.ParaphraseCommentDTO;
import com.cap.service.ParaphraseCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjx
 * @since 2021-03-05
 */
@Service
public class ParaphraseCommentServiceImpl extends ServiceImpl<ParaphraseCommentMapper, ParaphraseComment> implements ParaphraseCommentService {
    @Autowired
    ParaphraseCommentMapper mapper;
    public void addParaphraseComment(ParaphraseCommentForm paraphraseCommentForm) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        ParaphraseComment paraphraseComment = new ParaphraseComment();
        paraphraseComment.setUserId(paraphraseCommentForm.getUserId());
        paraphraseComment.setParaphraseComment(paraphraseCommentForm.getParaphraseComment());
        paraphraseComment.setLikeNum(0);
        paraphraseComment.setParaphraseId(paraphraseCommentForm.getParaphraseId());
        paraphraseComment.setCommentDate(new Date());
        paraphraseComment.setParaphraseCommentId(paraphraseCommentForm.getParaphraseId());
        save(paraphraseComment);
    }
    public List<ParaphraseCommentDTO> getParaphraseComment(Long paraphraseId, Long userId) {
        System.out.println("wedcat");
        /*QueryWrapper<ParaphraseComment> wrapper = new QueryWrapper<>();
        wrapper.eq("paraphrase_id",paraphraseId);
        wrapper.orderByDesc("paraphrase_comment_id");*/
        List<ParaphraseCommentDTO> list =  mapper.getById(paraphraseId, userId);
        //list.stream().map((item)->{item.setCommentDate(item.getCommentDate().toString())});

        return list;
    }

}
