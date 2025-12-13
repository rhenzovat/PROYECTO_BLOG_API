package com.cibertec.blogapp.infrastructure.controllers;

import com.cibertec.blogapp.application.usecases.BlogService;
import com.cibertec.blogapp.application.usecases.dto.request.CreateBlogRequest;
import com.cibertec.blogapp.application.usecases.dto.response.BlogResponse;
import com.cibertec.blogapp.domain.model.Blog;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping
    public BlogResponse create(@RequestBody CreateBlogRequest request) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return blogService.create(request, username);
    }

    @GetMapping
    public List<BlogResponse> findAll() {
        return blogService.findAll();
    }


//    me quede hasat aqui
    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(
            @PathVariable Long id,
            @RequestBody Blog blog,
            Authentication authentication
    ) {
        String username = authentication.getName();
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        Blog updated = blogService.update(id, blog, username, isAdmin);
        return ResponseEntity.ok(updated);
    }
}

