package com.cap.service.impl;

import com.cap.pojo.User;
import com.cap.mapper.UserMapper;
import com.cap.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
