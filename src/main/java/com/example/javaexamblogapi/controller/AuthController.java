package com.example.javaexamblogapi.controller;



import com.example.javaexamblogapi.dto.AuthenticationRequestDto;
import com.example.javaexamblogapi.dto.UserDto;
import com.example.javaexamblogapi.model.Role;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/api/auth/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthenticationRequestDto entity) {
        try {
            String username = entity.getUsername();
            String password = entity.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            User user = userService.findByUsername(username);
            if(user == null)
                throw new UsernameNotFoundException(String.format("User with username %s was not found", username));
            String token = jwtTokenProvider.createToken(username, user.getEmail(), user.getRoles());

            Map<String, String> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/api/auth/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody AuthenticationRequestDto entity) {
        try {
            UserDto userDto = new UserDto();
            userDto.setUsername(entity.getUsername());
            userDto.setEmail(entity.getEmail());
            userDto.setPassword(entity.getPassword());

            User user = userDto.toUser();

            User registeredUser = userService.register(user);

            Map<String, String> response = new HashMap<>();

            String token = jwtTokenProvider.createToken(registeredUser.getUsername(), registeredUser.getEmail(), registeredUser.getRoles());

            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid data");
        }
    }


}
