package com.cibertec.blogapp.infrastructure.database;

import com.cibertec.blogapp.application.usecases.dto.response.BlogHomeResponse;
import com.cibertec.blogapp.domain.model.Blog;
import com.cibertec.blogapp.domain.model.Comment; // Asegúrate de importar tu modelo Comment
import com.cibertec.blogapp.domain.model.Category;
import com.cibertec.blogapp.domain.services.BlogPersistencePort;
import com.cibertec.blogapp.infrastructure.database.entities.BlogEntity;
import com.cibertec.blogapp.infrastructure.database.respositories.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BlogPersistenceAdapter implements BlogPersistencePort {

    private final BlogRepository repository;

    // Método auxiliar para no repetir código de mapeo
    private Blog mapToDomain(BlogEntity entity) {
        List<Comment> domainComments = new ArrayList<>();
        if (entity.getComments() != null) {
            domainComments = entity.getComments().stream()
                    .map(c -> new Comment(
                            c.getId(),
                            entity.getId(), // blogId
                            c.getContent(),
                            c.getAuthorUsername(),
                            c.getCreatedAt()
                    ))
                    .collect(Collectors.toList());
        }

        return new Blog(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getAuthorUsername(),
                entity.getCreatedAt(),
                entity.getCategory(),
                domainComments // El 7mo parámetro
        );
    }

    @Override
    public Blog save(Blog blog) {
        BlogEntity entity;
        if (blog.getId() != null) {
            entity = repository.findById(blog.getId())
                    .orElseThrow(() -> new RuntimeException("BlogEntity no encontrada"));
        } else {
            entity = new BlogEntity();
            entity.setCreatedAt(blog.getCreatedAt());
        }

        entity.setTitle(blog.getTitle());
        entity.setContent(blog.getContent());
        entity.setAuthorUsername(blog.getAuthorUsername());
        entity.setCategory(blog.getCategory());

        BlogEntity saved = repository.save(entity);
        return mapToDomain(saved);
    }

    @Override
    public Optional<Blog> findById(Long id) {
        return repository.findById(id).map(this::mapToDomain);
    }

    @Override
    public List<Blog> findAll() {
        return repository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToDomain)
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
                .map(this::mapToDomain)
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