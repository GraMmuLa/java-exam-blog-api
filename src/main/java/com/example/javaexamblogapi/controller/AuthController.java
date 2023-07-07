package com.example.javaexamblogapi.controller;



import com.example.javaexamblogapi.dto.AuthenticationRequestDto;
import com.example.javaexamblogapi.model.User;
import com.example.javaexamblogapi.security.jwt.JwtTokenProvider;
import com.example.javaexamblogapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @RequestMapping(value = "/api/auth")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthenticationRequestDto entity) {
        try {
            String username = entity.getUsername();
            String password = entity.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            User user = userService.findByUsername(username);
            if(user == null)
                throw new UsernameNotFoundException(String.format("User with username %s was not found", username));
            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<String, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

}
