package com.example.javaexamblogapi.service;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public interface ApiService<T> {
    public List<T> getAll();
    public T findById(Long id);
    public T save(T entity);
    public void delete(T entity);
}
