package com.cibertec.blogapp.application.usecases;

import com.cibertec.blogapp.application.usecases.dto.request.CreateBlogRequest;
import com.cibertec.blogapp.application.usecases.dto.response.BlogResponse;
import com.cibertec.blogapp.domain.model.Blog;

import java.util.List;

public interface BlogService {

    BlogResponse create(CreateBlogRequest request, String username);

    List<BlogResponse> findAll();
    Blog update(Long id, Blog newBlog, String username, boolean isAdmin);

    void delete(Long id, boolean isAdmin);
}

