package com.cap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cap.dto.InfoContentDTO;
import com.cap.dto.SearchData;
import com.cap.form.EndoreForm;
import com.cap.form.InfoContentForm;
import com.cap.pojo.Info;
import com.cap.pojo.InfoContent;
import com.cap.mapper.InfoContentMapper;
import com.cap.pojo.InfoLike;
import com.cap.service.InfoContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
public class InfoContentServiceImpl extends ServiceImpl<InfoContentMapper, InfoContent> implements InfoContentService {
    @Autowired
    private InfoServiceImpl infoService;
    @Autowired
    private SearchServiceImpl searchService;
    @Autowired
    private InfoContentMapper mapper;

    public List<InfoContentDTO> getInfoContent (Long infoId, Long userId) {
        return mapper.getById(infoId, userId);
    }
    public void addInfoContent (Long userId, Long infoId, String content) {
        InfoContent infoContent = new InfoContent(null,infoId,userId,content,0);
        save(infoContent);
    }

    public Boolean changeFirst(Long contentId){
        QueryWrapper<InfoContent> infoContentQueryWrapper = new QueryWrapper<>();
        infoContentQueryWrapper.eq("info_content_id",contentId);
        InfoContent infoContent = getOne(infoContentQueryWrapper);

        QueryWrapper<Info> infoQueryWrapper = new QueryWrapper<>();
        infoQueryWrapper.eq("info_id",infoContent.getInfoId());
        Info info = infoService.getOne(infoQueryWrapper);
        Long infoContentId = info.getInfoContentId();

        infoContentQueryWrapper = new QueryWrapper<InfoContent>();
        infoContentQueryWrapper.eq("info_id",info.getInfoId());
        infoContentQueryWrapper.orderByDesc("like_num");
        infoContent = list(infoContentQueryWrapper).get(0);

        if (infoContent.getInfoContentId() != info.getInfoContentId()){

            info.setInfoContentId(infoContent.getInfoContentId());
            SearchData searchData = new SearchData();
            searchData.setEsId(info.getInfoId().toString());
            searchData.setCnName(info.getCnName());
            searchData.setEnName(info.getEnName());
            searchData.setSlug(info.getSlug());
            searchData.setContent(infoContent.getContent());
            searchService.changeSearchData(searchData);
            infoService.updateById(info);
        }
        return true;
    }
}
