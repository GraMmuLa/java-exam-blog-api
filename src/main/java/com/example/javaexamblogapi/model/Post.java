package com.example.javaexamblogapi.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Table(name="posts", indexes = {})
@Data
@Entity
public class Post {
    @Id
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="content", nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name="created_at", nullable = false)
    private Timestamp created_at;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_posts",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name="user_id"))
    private List<User> users;
}
