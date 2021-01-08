package com.cap.mapper;

import com.cap.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjx
 * @since 2021-01-08
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
