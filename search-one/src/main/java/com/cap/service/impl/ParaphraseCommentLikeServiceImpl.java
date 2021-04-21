package com.cap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cap.form.ParaphraseCommentLikeForm;
import com.cap.form.ScoreForm;
import com.cap.pojo.ParaphraseComment;
import com.cap.pojo.ParaphraseCommentLike;
import com.cap.mapper.ParaphraseCommentLikeMapper;
import com.cap.service.ParaphraseCommentLikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjx
 * @since 2021-03-05
 */
@Service
public class ParaphraseCommentLikeServiceImpl extends ServiceImpl<ParaphraseCommentLikeMapper, ParaphraseCommentLike> implements ParaphraseCommentLikeService {
    @Autowired
    UserServiceImpl usersService;
    @Autowired
    ParaphraseCommentServiceImpl paraphraseCommentService;

    public void like (ParaphraseCommentLikeForm likeForm) {
        ParaphraseCommentLike like = new ParaphraseCommentLike();
        like.setUserId(likeForm.getUserId());
        like.setParaphraseCommentId(likeForm.getParaphrase_comment_id());
        save(like);
        ParaphraseComment comment = paraphraseCommentService.getById(likeForm.getParaphrase_comment_id());
        comment.setLikeNum(comment.getLikeNum() + 1);
        paraphraseCommentService.updateById(comment);
        usersService.score(comment.getUserId(),2);
    }
    public void cancelLike (ParaphraseCommentLikeForm likeForm) {
        QueryWrapper<ParaphraseCommentLike> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",likeForm.getUserId());
        wrapper.eq("paraphrase_comment_id",likeForm.getParaphrase_comment_id());
        remove(wrapper);
        ParaphraseComment comment = paraphraseCommentService.getById(likeForm.getParaphrase_comment_id());
        comment.setLikeNum(comment.getLikeNum() - 1);
        paraphraseCommentService.updateById(comment);
        usersService.score(likeForm.getUserId(),-2);
    }
}
