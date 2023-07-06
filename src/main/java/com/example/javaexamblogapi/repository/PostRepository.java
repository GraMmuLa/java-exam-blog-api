package com.example.javaexamblogapi.repository;

import com.example.javaexamblogapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
    public Post findByTitle(String title);
}
