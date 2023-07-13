package com.example.javaexamblogapi.service;

import com.example.javaexamblogapi.model.Post;
import com.example.javaexamblogapi.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PostService implements ApiService<Post> {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAll() {
        List<Post> result = postRepository.findAll();
        log.info("INF: Post service getAll() completed successfully");
        return result;
    }

    @Override
    public Post findById(Long id) {
        Post result = postRepository.findById(id).orElse(null);
        if(result != null)
            log.info("INF: Post service findById() completed successfully with id {}.", id);
        else
            log.warn("WRN: Post service findById() completed and returned null");
        return result;
    }

    @Override
    public Post save(Post entity) {
        Post result = postRepository.save(entity);

        log.info("INF: Post service save() was completed successfully");

        return result;
    }

    @Override
    public void delete(Post entity) {
        postRepository.delete(entity);

        log.info("INF: Post service delete() was completed successfully");
    }
}
