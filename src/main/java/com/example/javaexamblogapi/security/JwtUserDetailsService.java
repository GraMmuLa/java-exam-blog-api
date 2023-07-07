package com.example.javaexamblogapi.security;

import com.example.javaexamblogapi.model.User;
import com.example.javaexamblogapi.security.jwt.JwtUser;
import com.example.javaexamblogapi.security.jwt.JwtUserFactory;
import com.example.javaexamblogapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = userService.findByUsername(username);

        if(foundUser == null)
            throw new UsernameNotFoundException(String.format("User with username %s not found", username));

        JwtUser jwtUser = JwtUserFactory.create(foundUser);

        log.info("INF: loadUserByUsername - user with username {} successfully loaded", username);

        return jwtUser;
    }
}
