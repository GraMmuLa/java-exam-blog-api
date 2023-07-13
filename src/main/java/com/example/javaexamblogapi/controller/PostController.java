package com.example.javaexamblogapi.controller;

import com.example.javaexamblogapi.model.Post;
import com.example.javaexamblogapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("api/post/fetch}")
    public ResponseEntity<Map<String, List<Post>>> fetchPosts(
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name="limit", required = false) Integer limit) {

        List<Post> resultList = postService.getAll();
        if(from != null && limit != null)
            resultList = resultList.subList(from, limit);

        Map<String, List<Post>> result = new HashMap<>();
        result.put("posts", resultList);

        return ResponseEntity.ok(result);
    }


}
