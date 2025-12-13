package com.cibertec.blogapp.domain.services;

import com.cibertec.blogapp.domain.model.Blog;

import java.util.List;
import java.util.Optional;

public interface BlogPersistencePort {

    Blog save(Blog blog);

    Optional<Blog> findById(Long id);

    List<Blog> findAll();

    void deleteById(Long id);
}

