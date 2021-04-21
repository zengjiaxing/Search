package com.cap.mapper;

import com.cap.pojo.ParaphraseComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cap.dto.ParaphraseCommentDTO;
import org.apache.ibatis.annotations.Mapper;
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
public interface ParaphraseCommentMapper extends BaseMapper<ParaphraseComment> {
    public List<ParaphraseCommentDTO> getById(Long paraphraseId, Long userId);
}
