package com.cap.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cap.mapper.RolePermissionMapper;
import com.cap.mapper.UserMapper;
import com.cap.mapper.UserRoleMapper;
import com.cap.pojo.RolePermission;
import com.cap.pojo.User;
import com.cap.pojo.UserRole;
import com.cap.token.JWTToken;
import com.cap.util.JWTUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
@Component
public class CustomRealm extends AuthorizingRealm {
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private void setUserMapper (UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }
    /*
    * 获取身份验证信息
    * shiro中，最终是通过Realm来获取应用程序中的用户、角色及权限信息的。
    * @param authenticationToken 用户身份信息token
    * @return 返回封装了用户信息的AuthenticationInfo实例
    * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("-----身份认证方法-----");
        //UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //从数据库获取对应用户名密码的用户
        String token = (String) authenticationToken.getCredentials();
        String username = JWTUtil.getUsername(token);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        User user = userMapper.selectOne(wrapper);
        if (username == null || !JWTUtil.verify(token, username)) {
            throw new AuthenticationException("token认证失败！");
        }
        if (null == user.getPwd()) {
            throw new AccountException("该用户不存在");
        }
        return new SimpleAuthenticationInfo(token,token,"CustomRealm");
    }
    /*
    * 获取授权信息
    *
    * @param principalCollection
    * @return
    * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("-----权限认证----");
        //String username = (String) SecurityUtils.getSubject().getPrincipal();
        String username = JWTUtil.getUsername(principalCollection.toString());
        System.out.println(principalCollection.toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
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
        info.setRoles(roleSet);
        info.setStringPermissions(permissionSet);
        return info;
    }

}
