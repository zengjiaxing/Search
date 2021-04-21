package com.cap.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cap.mapper.RolePermissionMapper;
import com.cap.mapper.UserRoleMapper;
import com.cap.pojo.RolePermission;
import com.cap.pojo.UserRole;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ShiroUtil {
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    RolePermissionMapper rolePermissionMapper;
    public List<Set<String>> getPermission (String username) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        List<UserRole> list = userRoleMapper.selectList(wrapper);
        Iterator<UserRole> iterator = list.iterator();
        Set<String> roleSet = new HashSet<>();
        Set<String> permissionSet = new HashSet<>();
        while (iterator.hasNext()) {
            UserRole userRole = iterator.next();
            roleSet.add(userRole.getRoleName());

            QueryWrapper<RolePermission> permissionQueryWrapper = new QueryWrapper<>();
            permissionQueryWrapper.eq("role_name",userRole.getRoleName());
            List<RolePermission> permissions = rolePermissionMapper.selectList(permissionQueryWrapper);
            Iterator<RolePermission> permissionIterator = permissions.iterator();
            while (permissionIterator.hasNext()) {
                RolePermission rolePermission = permissionIterator.next();
                permissionSet.add(rolePermission.getPermission());
            }
        }
        List<Set<String>> listSet = new ArrayList<>();
        listSet.add(roleSet);
        listSet.add(permissionSet);
        return listSet;
    }
}
