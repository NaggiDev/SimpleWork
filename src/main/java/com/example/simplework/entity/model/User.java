package com.example.simplework.entity.model;

import com.example.simplework.constant.UserPrivilegeConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_account")
public class User implements UserDetails {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "USER_NAME", nullable = false, length = 10)
    private String userName;

    @Column(name = "PASSWORD", length = 16)
    private String password;

    @Column(name = "EMAIL", length = 50)
    private String email;

    @Column(name = "USER_PRIVILEGE")
    private Integer userPrivilege;

    @Column(name = "ENABLED", length = 11)
    private Integer enabled;

    @Column(name = "CREATED_AT", nullable = false)
    private Date createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private Date updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        String role = UserPrivilegeConstant.fromValue(userPrivilege).toString();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
