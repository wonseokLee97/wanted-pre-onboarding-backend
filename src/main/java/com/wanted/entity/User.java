package com.wanted.entity;


import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Builder
@Table(name = "user")
@DynamicInsert
@DynamicUpdate
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    //상태 다루지 않으면 모두 true
    @Override
    public boolean isAccountNonExpired() { //계정이 만료됐는지 리턴

        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //계정이 잠겨있는지 리턴

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //비밀번호가 만료됐는지 리턴

        return true;
    }

    @Override
    public boolean isEnabled() { //계정이 활성화돼 있는지 리턴
        return true;
    }
}