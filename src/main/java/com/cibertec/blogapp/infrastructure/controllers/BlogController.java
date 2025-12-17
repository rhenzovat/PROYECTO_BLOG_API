package com.cibertec.blogapp.infrastructure.controllers;

import com.cibertec.blogapp.application.usecases.BlogService;
import com.cibertec.blogapp.application.usecases.dto.request.CreateBlogRequest;
import com.cibertec.blogapp.application.usecases.dto.response.BlogHomeResponse;
import com.cibertec.blogapp.application.usecases.dto.response.BlogResponse;
import com.cibertec.blogapp.domain.model.Blog;
import com.cibertec.blogapp.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping
    public BlogResponse create(
            @RequestBody CreateBlogRequest request,
            Authentication authentication
    ) {
        if (authentication == null ||
                authentication.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("Usuario no autenticado");
        }

        String username = authentication.getName();
        return blogService.create(request, username);
    }
//    @PostMapping
//    public BlogResponse create(@RequestBody CreateBlogRequest request) {
//
//        String username = SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getName();
//
//        return blogService.create(request, username);
//    }

//    @GetMapping
//    public List<BlogResponse> findAll() {
//        System.out.println("ENTRÓ AL CONTROLLER");
//        return blogService.findAll();
//    }


//    me quede hasta aqui
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            Authentication authentication
    ) {

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        authentication.getAuthorities()
                .forEach(a -> System.out.println(a.getAuthority()));
        blogService.delete(id, isAdmin);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<BlogResponse> findAll(
            @RequestParam(required = false) Category category
    ) {
        return blogService.findAll(category);
    }

    @GetMapping("/home")
    public List<BlogHomeResponse> home(
            @RequestParam(defaultValue = "recentes") String tipo,
            @RequestParam(required = false) Category category
    ) {
        return blogService.getHomeBlogs(tipo, category);
    }

}

