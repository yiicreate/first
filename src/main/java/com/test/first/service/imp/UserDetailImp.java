package com.test.first.service.imp;

import com.test.first.entity.User;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
import java.util.Collection;

@NoArgsConstructor
public class UserDetailImp implements UserDetails {

    @Setter
    private Long id;// 用户id
    @Setter
    private String username;// 用户名
    @Setter
    private String password;// 密码
    private String userRoles;// 用户权限集合
    private String roleMenus;// 角色菜单集合

    public UserDetailImp(User user){
        username = user.getUserName();
        password = user.getPassWord();
    }

    public UserDetailImp(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
//        this.roleMenus = roleMenus;
    }

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账户是否未锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 密码是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 账户是否激活
    @Override
    public boolean isEnabled() {
        return true;
    }
}
