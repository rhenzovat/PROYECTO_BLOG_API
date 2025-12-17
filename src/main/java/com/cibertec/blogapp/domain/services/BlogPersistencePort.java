package com.cibertec.blogapp.domain.services;

import com.cibertec.blogapp.application.usecases.dto.response.BlogHomeResponse;
import com.cibertec.blogapp.domain.model.Blog;
import com.cibertec.blogapp.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface BlogPersistencePort {

    Blog save(Blog blog);

    Optional<Blog> findById(Long id);

    List<Blog> findAll();

    void deleteById(Long id);

    List<Blog> findByCategory(Category category);

    List<BlogHomeResponse> findRecentBlogs();
    List<BlogHomeResponse> findMostCommentedBlogs();
    List<BlogHomeResponse> findHomeByCategory(Category category);
}

