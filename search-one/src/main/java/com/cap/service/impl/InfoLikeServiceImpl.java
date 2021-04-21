package com.cap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cap.form.EndoreForm;
import com.cap.form.ScoreForm;
import com.cap.pojo.InfoContent;
import com.cap.pojo.InfoLike;
import com.cap.mapper.InfoLikeMapper;
import com.cap.service.InfoLikeService;
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
public class InfoLikeServiceImpl extends ServiceImpl<InfoLikeMapper, InfoLike> implements InfoLikeService {
    @Autowired
    InfoContentServiceImpl infoContentService;
    @Autowired
    UserServiceImpl usersService;

    public void endorse (Long contentId, Long userId) {
        InfoLike infoLike = new InfoLike();
        infoLike.setContentId(contentId);
        infoLike.setUserId(userId);
        save(infoLike);
        InfoContent infoContent = infoContentService.getById(contentId);
        infoContent.setLikeNum(infoContent.getLikeNum() + 1);
        infoContentService.updateById(infoContent);
        usersService.score(infoContentService.getById(contentId).getUserId(),5);
        infoContentService.changeFirst(contentId);
    }
    public void cancelEndorse (Long contentId, Long userId){
        QueryWrapper<InfoLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("content_id",contentId);
        remove(queryWrapper);
        InfoContent infoContent = infoContentService.getById(contentId);
        infoContent.setLikeNum(infoContent.getLikeNum() - 1);
        infoContentService.updateById(infoContent);

        usersService.score(infoContentService.getById(contentId).getUserId(),-5);
        infoContentService.changeFirst(contentId);
    }
}
