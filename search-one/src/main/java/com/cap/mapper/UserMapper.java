package com.cap.mapper;

import com.cap.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjx
 * @since 2021-01-08
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
