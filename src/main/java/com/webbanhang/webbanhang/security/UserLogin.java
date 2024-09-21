package com.webbanhang.webbanhang.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.webbanhang.webbanhang.Model.UserModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLogin implements UserDetails {
    private String email;
    private String password;
    private String fullName;
    private List<GrantedAuthority> authorities;

    public UserLogin(UserModel userInfo)
    {
        this.email = userInfo.getEmail();
        this.password = userInfo.getPassword();
        this.fullName = userInfo.getUserName();
        this.authorities = Arrays.stream(userInfo.getRole().getRoleName().split(","))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.trim())) // Ensure roles are prefixed with "ROLE_"
                .collect(Collectors.toList());
    }

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
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement your logic here
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement your logic here
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement your logic here
    }

    @Override
    public boolean isEnabled() {
        return true; // Implement your logic here
    }
}
