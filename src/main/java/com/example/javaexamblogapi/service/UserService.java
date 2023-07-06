package com.example.javaexamblogapi.service;

import com.example.javaexamblogapi.repository.PostRepository;
import com.example.javaexamblogapi.repository.RoleRepository;
import com.example.javaexamblogapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PostRepository postRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PostRepository postRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.postRepository = postRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }
}
