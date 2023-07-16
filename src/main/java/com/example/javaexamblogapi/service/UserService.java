package com.example.javaexamblogapi.service;

import com.example.javaexamblogapi.model.Role;
import com.example.javaexamblogapi.model.User;
import com.example.javaexamblogapi.repository.PostRepository;
import com.example.javaexamblogapi.repository.RoleRepository;
import com.example.javaexamblogapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.core.RepositoryCreationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService implements ApiService<User> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PostRepository postRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User save(User user) throws RepositoryCreationException {
        Role roleForUser = roleRepository.findByName("USER").orElse(null);

        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleForUser);

        user.setRoles(userRoles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            User savedUser = userRepository.save(user);

            log.info("INF: user with username {} was successfully registered", user.getUsername());

            return savedUser;
        } catch (RepositoryCreationException e) {

            log.error("Failed to create user with username {} and email {}", user.getUsername(), user.getEmail());

            throw new RepositoryCreationException("Failed to create user", RepositoryCreationException.class);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> allUsers = userRepository.findAll();
        log.info("INF: getAll - found {} users", allUsers.size());
        return allUsers;
    }

    public User findByUsername(String username) {
        User foundUser = userRepository.findByUsername(username).orElse(null);

        if(foundUser != null)
            log.info("INF: findByUsername - got user with username {}", username);
        else
            log.warn("WRN: findByUsername - got no users with username {}", username);

        return foundUser;
    }

    @Override
    public User findById(Long id) {
       User foundUser = userRepository.findById(id).orElse(null);

        if(foundUser != null)
            log.info("INF: findById - got user with id {}", id);
        else
            log.warn("WRN: findById - got no users with id {}", id);

        return foundUser;
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);

        log.info("INF: user with username {} was successfully deleted", user.getUsername());
    }
}
