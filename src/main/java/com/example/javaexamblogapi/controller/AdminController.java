package com.example.javaexamblogapi.controller;

import com.example.javaexamblogapi.dto.UserDto;
import com.example.javaexamblogapi.model.User;
import com.example.javaexamblogapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value="api/admin/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        User foundUser = userService.findById(id);

        if(foundUser == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        UserDto result = UserDto.fromUser(foundUser);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
