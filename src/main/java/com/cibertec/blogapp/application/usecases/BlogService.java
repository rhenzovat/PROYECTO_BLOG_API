package com.cibertec.blogapp.application.usecases;

import com.cibertec.blogapp.application.usecases.dto.request.CreateBlogRequest;
import com.cibertec.blogapp.application.usecases.dto.response.BlogHomeResponse;
import com.cibertec.blogapp.application.usecases.dto.response.BlogResponse;
import com.cibertec.blogapp.domain.model.Blog;
import com.cibertec.blogapp.domain.model.Category;

import java.util.List;

public interface BlogService {

    BlogResponse create(CreateBlogRequest request, String username);

    List<BlogResponse> findAll();

    Blog update(Long id, Blog newBlog, String username, boolean isAdmin);

    void delete(Long id, boolean isAdmin, String username);

    List<BlogResponse> findAll(Category category);

    List<BlogHomeResponse> getHomeBlogs(String type, Category category);

    BlogResponse findById(Long id);
}



