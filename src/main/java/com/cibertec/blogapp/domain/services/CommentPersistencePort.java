package com.cibertec.blogapp.domain.services;

import com.cibertec.blogapp.domain.model.Comment;

import java.util.List;

public interface CommentPersistencePort {

    Comment save(Comment comment);

    List<Comment> findByBlogId(Long blogId);

    Comment findById(Long id);

    void deleteById(Long id);



}
