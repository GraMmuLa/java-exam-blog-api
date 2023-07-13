package com.example.javaexamblogapi.controller;



import com.example.javaexamblogapi.dto.AuthenticationRequestDto;
import com.example.javaexamblogapi.dto.PostDto;
import com.example.javaexamblogapi.dto.PostRequestDto;
import com.example.javaexamblogapi.dto.UserDto;
import com.example.javaexamblogapi.model.Role;
import com.example.javaexamblogapi.model.User;
import com.example.javaexamblogapi.security.jwt.JwtTokenProvider;
import com.example.javaexamblogapi.security.jwt.JwtUser;
import com.example.javaexamblogapi.security.jwt.exceptions.JwtAuthenticationException;
import com.example.javaexamblogapi.service.PostService;
import com.example.javaexamblogapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public AuthController(PostService postService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.postService = postService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/api/auth/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthenticationRequestDto entity) {
        try {
            String username = entity.getUsername();
            String password = entity.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            User user = userService.findByUsername(username);
            if(user == null)
                throw new UsernameNotFoundException(String.format("User with username %s was not found", username));
            String token = jwtTokenProvider.createToken(user.getId(), username, user.getEmail(), user.getRoles());

            Map<String, String> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @CrossOrigin(origins = "*", exposedHeaders = {"Access-Control-Allow-Origin"})
    @GetMapping("/api/auth/checkToken")
    public ResponseEntity<Map<String, Object>> checkToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String trimmedToken = token.substring(7);
        Map<String, Object> response = new HashMap<>();

        if(jwtTokenProvider.validateToken(trimmedToken)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(trimmedToken);

            User user = userService.findByUsername(authentication.getName());

            String refreshedToken = jwtTokenProvider.createToken(user.getId(), user.getUsername(), user.getEmail(), user.getRoles());

            response.put("token", refreshedToken);

            return ResponseEntity.ok(response);
        }
        else {
            response.put("message", "Expired token");

            return ResponseEntity.badRequest().body(response);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/api/auth/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody AuthenticationRequestDto entity) {
        try {
            UserDto userDto = new UserDto();
            userDto.setUsername(entity.getUsername());
            userDto.setEmail(entity.getEmail());
            userDto.setPassword(entity.getPassword());

            User user = userDto.toEntity();

            User registeredUser = userService.save(user);

            Map<String, String> response = new HashMap<>();

            String token = jwtTokenProvider.createToken(registeredUser.getId(), registeredUser.getUsername(), registeredUser.getEmail(), registeredUser.getRoles());

            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid data");
        }
    }

}
