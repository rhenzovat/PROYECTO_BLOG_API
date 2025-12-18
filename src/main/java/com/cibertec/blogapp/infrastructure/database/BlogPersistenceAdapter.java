package com.cibertec.blogapp.infrastructure.database;

import com.cibertec.blogapp.application.usecases.dto.response.BlogHomeResponse;
import com.cibertec.blogapp.domain.model.Blog;
import com.cibertec.blogapp.domain.model.Category;
import com.cibertec.blogapp.domain.services.BlogPersistencePort;
import com.cibertec.blogapp.infrastructure.database.entities.BlogEntity;
import com.cibertec.blogapp.infrastructure.database.respositories.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BlogPersistenceAdapter implements BlogPersistencePort {

    private final BlogRepository repository;

    @Override
    public Blog save(Blog blog) {

        BlogEntity entity;

        if (blog.getId() != null) {
            // UPDATE
            entity = repository.findById(blog.getId())
                    .orElseThrow(() -> new RuntimeException("BlogEntity no encontrada"));
        } else {
            // INSERT
            entity = new BlogEntity();
            entity.setCreatedAt(blog.getCreatedAt());
        }

        entity.setTitle(blog.getTitle());
        entity.setContent(blog.getContent());
        entity.setAuthorUsername(blog.getAuthorUsername());
        entity.setCategory(blog.getCategory());

        BlogEntity saved = repository.save(entity);

        return new Blog(
                saved.getId(),
                saved.getTitle(),
                saved.getContent(),
                saved.getAuthorUsername(),
                entity.getCreatedAt(),
                entity.getCategory()
        );
    }

    @Override
    public Optional<Blog> findById(Long id) {
        return repository.findById(id)
                .map(e -> new Blog(
                        e.getId(),
                        e.getTitle(),
                        e.getContent(),
                        e.getAuthorUsername(),
                        e.getCreatedAt(),
                        e.getCategory()
                ));
    }

    @Override
    public List<Blog> findAll() {
        return repository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(e -> new Blog(
                        e.getId(),
                        e.getTitle(),
                        e.getContent(),
                        e.getAuthorUsername(),
                        e.getCreatedAt(),
                        e.getCategory()
                ))
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Blog> findByCategory(Category category) {
        return repository.findByCategoryOrderByCreatedAtDesc(category)
                .stream()
                .map(e -> new Blog(
                        e.getId(),
                        e.getTitle(),
                        e.getContent(),
                        e.getAuthorUsername(),
                        e.getCreatedAt(),
                        e.getCategory()

                ))
                .toList();
    }

    @Override
    public List<BlogHomeResponse> findRecentBlogs(Pageable pageable) {
        return repository.findRecentBlogs(pageable);
    }

    @Override
    public List<BlogHomeResponse> findMostCommentedBlogs(Pageable pageable) {
        return repository.findMostCommentedBlogs(pageable);
    }

    @Override
    public List<BlogHomeResponse> findHomeByCategory(Category category, Pageable pageable) {
        return repository.findHomeByCategory(category, pageable);
    }

}
