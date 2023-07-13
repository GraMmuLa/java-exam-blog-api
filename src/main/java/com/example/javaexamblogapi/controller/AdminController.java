package com.example.javaexamblogapi.controller;

import com.example.javaexamblogapi.dto.AuthenticationRequestDto;
import com.example.javaexamblogapi.dto.PostDto;
import com.example.javaexamblogapi.dto.PostRequestDto;
import com.example.javaexamblogapi.dto.UserDto;
import com.example.javaexamblogapi.model.Post;
import com.example.javaexamblogapi.model.User;
import com.example.javaexamblogapi.service.PostService;
import com.example.javaexamblogapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(maxAge = 3600)
public class AdminController {

    private final UserService userService;
    private final PostService postService;

    @Autowired
    public AdminController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/api/admin/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        User foundUser = userService.findById(id);

        if(foundUser == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        UserDto result = UserDto.fromEntity(foundUser);

        ResponseEntity<UserDto> entity = new ResponseEntity<>(result, HttpStatus.OK);
        return entity;
    }

    @CrossOrigin(origins = "*")
    @PostMapping( value="/api/admin/post/add/{userId}")
    public ResponseEntity addPost(@RequestBody PostRequestDto postRequestDto, @PathVariable(name="userId") Long userId) {
        PostDto postDto = PostDto.fromEntity(postRequestDto);

        Post result = postDto.toEntity();

        postService.save(result);

        System.out.println(userId);

        ResponseEntity entity = new ResponseEntity<>(HttpStatus.OK);
        return entity;
    }

}
