package com.cibertec.blogapp.application.usecases;

import com.cibertec.blogapp.application.usecases.dto.request.CreateBlogRequest;
import com.cibertec.blogapp.application.usecases.dto.response.BlogResponse;

import java.util.List;

public interface BlogService {

    BlogResponse create(CreateBlogRequest request, String username);

    List<BlogResponse> findAll();
}

