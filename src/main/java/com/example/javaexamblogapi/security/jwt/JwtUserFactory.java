package com.example.javaexamblogapi.security.jwt;

import com.example.javaexamblogapi.model.Role;
import com.example.javaexamblogapi.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// От него нельзя наследоваться(фишка final class)
public final class JwtUserFactory {
    public JwtUserFactory() {

    }

    public static JwtUser create(User user) {
        return new JwtUser(user.getId(),
                user.getUsername()
                , user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRoles()));
    }

    private static Collection<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
        return roles.stream().map(x->new SimpleGrantedAuthority(x.getName())).collect(Collectors.toList());
    }
}
