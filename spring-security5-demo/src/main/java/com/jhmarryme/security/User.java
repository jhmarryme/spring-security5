package com.jhmarryme.security;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class User implements UserDetails {

    @Id
    private Long id;

    @Pattern(regexp = "^[a-zA-z]\\w{5,11}$", message = "用户名: 6-12个字符，可使用字母、数字、下划线，需要以字母开头")
    private String username;

    @Pattern(regexp = "^\\d*[a-zA-z~!@#$%^&*][a-zA-z0-9~!@#$%^&*]*$", message = "密码：8-16个字符，区分大小写，不能为纯数字，可包括特殊字符")
    private String password;

    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}