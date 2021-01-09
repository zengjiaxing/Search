package com.cap.service.impl;

import com.cap.pojo.Comment;
import com.cap.mapper.CommentMapper;
import com.cap.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjx
 * @since 2021-01-09
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
