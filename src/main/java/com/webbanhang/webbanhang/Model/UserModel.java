package com.webbanhang.webbanhang.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Entity(name = "UserModel")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_tb")
public class UserModel implements UserDetails {


    @Id
    @Column
    private String userID;

    @Column
    private String userName;
    @Column
    private String address;
    @Column
    private String phone;
    @Column
    private String gender;
    @Column
    private String email;
    @Column
    private String password;

    @Column
    private boolean active;

    @ManyToOne()
    @JsonManagedReference
    @JoinColumn(name = "type")
    private RoleModel role;


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "userCart", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CartModel> userCart;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userOrder", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderModel> userOrder;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sellerOrder", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderModel> sellerOrder;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shipperOrder", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderModel> shipperOrder;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(role.getRoleName()));
    }

    @Override
    public String getUsername() {
        return this.userName;
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
