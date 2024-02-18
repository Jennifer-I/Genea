package com.genea.security;

import com.genea.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class AppUserDetails implements UserDetails {
    private String password;
    private String email;
    private String fullName;
    private Boolean isEnabled;
    private List<GrantedAuthority> authorities;

    public AppUserDetails(User user) {
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.isEnabled = user.getIsVerified();
        this.authorities = Stream.of(new SimpleGrantedAuthority(user.getRole().name()))
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
