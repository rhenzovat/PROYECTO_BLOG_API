package com.cibertec.blogapp.domain.services;

import com.cibertec.blogapp.domain.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentPersistencePort {

    Comment save(Comment comment);

    List<Comment> findByBlogId(Long blogId);

    Optional<Comment> findById(Long id);

    void deleteById(Long id);



}
