package com.cap.mapper;

import com.cap.dto.InfoContentDTO;
import com.cap.dto.ParaphraseCommentDTO;
import com.cap.pojo.Info;
import com.cap.pojo.InfoContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjx
 * @since 2021-03-05
 */
@Mapper
@Repository
public interface InfoContentMapper extends BaseMapper<InfoContent> {
    @Options(useGeneratedKeys = true, keyProperty = "infoContentId", keyColumn = "info_content_id")
    @Insert("insert into info_content (info_id,user_id,content,like_num) values (#{infoId},#{userId},#{content},#{likeNum})")
    int insert(InfoContent infoContent);

    public List<InfoContentDTO> getById(Long infoId, Long userId);
}
