package com.cibertec.blogapp.application.usecases.impl;

import com.cibertec.blogapp.application.usecases.BlogService;
import com.cibertec.blogapp.application.usecases.dto.request.CreateBlogRequest;
import com.cibertec.blogapp.application.usecases.dto.response.BlogHomeResponse;
import com.cibertec.blogapp.application.usecases.dto.response.BlogResponse;
import com.cibertec.blogapp.domain.model.Blog;
import com.cibertec.blogapp.domain.model.Category;
import com.cibertec.blogapp.domain.services.BlogPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogPersistencePort blogPort;

    @Override
    public BlogResponse create(CreateBlogRequest request, String username) {

        if (request.getCategory() == null) {
            throw new RuntimeException("La categoría es obligatoria");
        }

        Blog blog = new Blog(
                null,
                request.getTitle(),
                request.getContent(),
                username,
                LocalDateTime.now(),
                request.getCategory()

        );

        Blog saved = blogPort.save(blog);

        return new BlogResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getContent(),
                saved.getAuthorUsername(),
                saved.getCreatedAt(),
                saved.getCategory()

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
                        b.getCategory()


                ))
                .toList();
    }

    @Override
    public Blog update(Long id, Blog newBlog, String username, boolean isAdmin) {

        Blog existingBlog = blogPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog no encontrado"));

        if (existingBlog == null) {
            throw new RuntimeException("Blog no encontrado");
        }

        if (!existingBlog.getAuthorUsername().equals(username) && !isAdmin) {
            throw new RuntimeException("No tienes permiso para editar este blog");
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
                .orElseThrow(() -> new RuntimeException("Blog no encontrado"));

        boolean isAuthor = blog.getAuthorUsername().equals(username);

        if (!isAdmin && !isAuthor) {
            throw new RuntimeException("No tienes permiso para eliminar este blog");
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
                        b.getCategory()

                ))
                .toList();
    }

    @Override
    public List<BlogHomeResponse> getHomeBlogs(
            String type,
            Category category
    ) {
        return switch (type) {
            case "comentados" -> blogPort.findMostCommentedBlogs();
            case "categoria" -> blogPort.findHomeByCategory(category);
            default -> blogPort.findRecentBlogs();
        };
    }



}

