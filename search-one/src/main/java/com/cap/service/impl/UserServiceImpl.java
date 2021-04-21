package com.cap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cap.form.ScoreForm;
import com.cap.pojo.User;
import com.cap.mapper.UserMapper;
import com.cap.pojo.UserRole;
import com.cap.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiOperation;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserRoleServiceImpl userRoleService;
    public void score (Long userId, int score) {
        User user = new User();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        user = getOne(wrapper);
        user.setScore(user.getScore() + score);
        updateById(user);
        Long endScore = user.getScore() + score;
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        UpdateWrapper<UserRole> userRoleUpdateWrapper = new UpdateWrapper<>();
        userRoleUpdateWrapper.eq("username",user.getUsername());
        userRoleQueryWrapper.eq("username",user.getUsername());
        UserRole userRole = userRoleService.getOne(userRoleQueryWrapper);
        if (endScore < 10) {
            if (userRole != null) {
                userRoleService.remove(userRoleQueryWrapper);
            }
        } else if (endScore >= 10 && endScore <= 20) {
            userRole.setRoleName("level_one");
            if (userRole == null) {
                userRoleService.save(userRole);
            } else {
                userRoleService.update(userRole,userRoleUpdateWrapper);
            }
        } else if (endScore > 20){
            userRole.setRoleName("level_two");
            if (userRole == null) {
                userRoleService.save(userRole);
            } else {
                userRoleService.update(userRole,userRoleUpdateWrapper);
            }
        }
    }
}
