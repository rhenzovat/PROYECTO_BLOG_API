package com.cibertec.blogapp.application.usecases.impl;

import com.cibertec.blogapp.application.usecases.BlogService;
import com.cibertec.blogapp.application.usecases.dto.request.CreateBlogRequest;
import com.cibertec.blogapp.application.usecases.dto.response.BlogHomeResponse;
import com.cibertec.blogapp.application.usecases.dto.response.BlogResponse;
import com.cibertec.blogapp.application.usecases.dto.response.CommentResponse;
import com.cibertec.blogapp.domain.model.Blog;
import com.cibertec.blogapp.domain.model.Category;
import com.cibertec.blogapp.domain.services.BlogPersistencePort;
import com.cibertec.blogapp.exception.BadRequestException;
import com.cibertec.blogapp.exception.ForbiddenException;
import com.cibertec.blogapp.exception.ResourceNotFoundException;
import com.cibertec.blogapp.infrastructure.database.entities.BlogEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogPersistencePort blogPort;

    @Override
    public BlogResponse create(CreateBlogRequest request, String username) {

        if (request.getCategory() == null) {
            throw new BadRequestException("La categoría es obligatoria");
        }

        Blog blog = new Blog(
                null,
                request.getTitle(),
                request.getContent(),
                username,
                LocalDateTime.now(),
                request.getCategory(),
                new java.util.ArrayList<>() // El 7mo parámetro
        );

        Blog saved = blogPort.save(blog);

        return new BlogResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getContent(),
                saved.getAuthorUsername(),
                saved.getCreatedAt(),
                saved.getCategory(),
                new ArrayList<>()

        );
    }

    @Override
    public List<BlogResponse> findAll() {
        return blogPort.findAll()
                .stream()
                .map(b -> new BlogResponse(
                        b.getId(),
                        b.getTitle(),
                        b.getContent(),
                        b.getAuthorUsername(),
                        b.getCreatedAt(),
                        b.getCategory(),
                        new ArrayList<>()


                ))
                .toList();
    }

    @Override
    public Blog update(Long id, Blog newBlog, String username, boolean isAdmin) {

        Blog existingBlog = blogPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog no encontrado"));

        if (!existingBlog.getAuthorUsername().equals(username) && !isAdmin) {
            throw new ForbiddenException("No tienes permiso para editar este blog");
        }

        existingBlog.update(
                newBlog.getTitle(),
                newBlog.getContent()
        );

        return blogPort.save(existingBlog);
    }

    @Override
    public void delete(Long id, boolean isAdmin, String username) {

        Blog blog = blogPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog no encontrado"));

        boolean isAuthor = blog.getAuthorUsername().equals(username);

        if (!isAdmin && !isAuthor) {
            throw new ForbiddenException("No tienes permiso para borrar este blog");
        }

        blogPort.deleteById(id);
    }

    @Override
    public List<BlogResponse> findAll(Category category) {

        List<Blog> blogs = (category != null)
                ? blogPort.findByCategory(category)
                : blogPort.findAll();

        return blogs.stream()
                .map(b -> new BlogResponse(
                        b.getId(),
                        b.getTitle(),
                        b.getContent(),
                        b.getAuthorUsername(),
                        b.getCreatedAt(),
                        b.getCategory(),
                        new ArrayList<>()

                ))
                .toList();
    }

    @Override
    public List<BlogHomeResponse> getHomeBlogs(
            String type,
            Category category
    ) {
        Pageable pageable = PageRequest.of(0, 6);

        return switch (type) {
            case "comentados" -> blogPort.findMostCommentedBlogs(pageable);
            case "categoria" -> blogPort.findHomeByCategory(category, pageable);
            default -> blogPort.findRecentBlogs(pageable);
        };
    }

    @Override
    public BlogResponse findById(Long id) {
        // 1. Usamos blogPort (que ya lo tienes inyectado arriba) en lugar de blogRepository
        // 2. Buscamos el objeto de dominio Blog
        Blog blog = blogPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog no encontrado con ID: " + id));
        List<CommentResponse> commentResponses = blog.getComments().stream()
                .map(c -> new CommentResponse(
                        c.getId(),
                        c.getContent(),
                        c.getAuthorUsername(),
                        c.getCreatedAt()
                ))
                .toList();
        // 3. Mapeamos el objeto de dominio Blog al DTO BlogResponse
        return new BlogResponse(
                blog.getId(),
                blog.getTitle(),
                blog.getContent(),
                blog.getAuthorUsername(),
                blog.getCreatedAt(),
                blog.getCategory(),
                commentResponses
        );
    }

}

