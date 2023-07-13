package com.example.javaexamblogapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Table(name="users", indexes = {})
@Data
@Entity
public class User {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="username", length = 64, nullable = false)
    private String username;

    @Column(name="email", length = 64, nullable = false)
    private String email;

    @Column(name="password", length = 64, nullable = false)
    private String password;

    @CreationTimestamp
    @Column(name="created_at", nullable = false)
    private Timestamp created_at;

    @JsonIgnore
    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Post> posts;

    @JsonIgnore
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Role> roles;
}
