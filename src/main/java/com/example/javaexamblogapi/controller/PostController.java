package com.example.javaexamblogapi.controller;

import com.example.javaexamblogapi.model.Post;
import com.example.javaexamblogapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("api/post/fetch")
    public ResponseEntity<List<Post>> fetchPosts(
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name="limit", required = false) Integer limit) {

        List<Post> resultList = postService.getAll();
        if(from != null && limit != null)
            resultList = resultList.subList(from, (limit < resultList.size() ? limit : resultList.size()));

        return ResponseEntity.ok(resultList);
    }

}
