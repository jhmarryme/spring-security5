package com.jhmarryme.service.security;

import com.jhmarryme.pojo.entity.security.Role;
import com.jhmarryme.pojo.entity.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置用户信息校验逻辑
 * @author JiaHao Wang
 * @date 2020/10/29 12:35
 */
@Component
@Slf4j
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("表单登录用户名: {}", username);
        return buildUser(username);
    }

    private User buildUser(String userId) {
        // 根据用户名查找用户

        // 根据查到的用户信息判断用户状态是否正常

        // 模拟加密后的密码
        String password = passwordEncoder.encode("123456");
        log.info("数据库密码为: {}", password);
        // 使用工具类将字符串转换为 需要的授权对象list

        User u = new User();
        u.setUsername(userId);
        u.setPassword(password);

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_ADMIN");
        roles.add(role);

        u.setRoles(roles);
        return u;
    }
}
