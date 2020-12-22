package com.dgut.service.Impl;

import com.dgut.entity.RoleEntity;
import com.dgut.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserServiceImpl authUserServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername");
        if(StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("UserDetailsService没有接收到用户账号");
        } else {
            /**
             * 根据用户名查找用户信息
             */
            UserEntity authUser = authUserServiceImpl.findByUsername(username);
            if(authUser == null) {
//                System.out.println(authUser);
                throw new UsernameNotFoundException(String.format("用户'%s'不存在", username));
            }
            List<RoleEntity> roleList = authUserServiceImpl.selectRolesByUserId(authUser.getUserId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (RoleEntity role : roleList) {
                //封装用户信息和角色信息到SecurityContextHolder全局缓存中
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            }
//            System.out.println(authUser.getRoles());
            // TODO
//            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_user"));
//            grantedAuthorities.add(new SimpleGrantedAuthority(authUser.getRoles()));
            /**
             * 创建一个用于认证的用户对象并返回，包括：用户名，密码，角色
             */
//            System.out.println(authUser.getUsername());
            return new User(authUser.getUserId(), authUser.getPassword(), grantedAuthorities);
        }
    }
}
