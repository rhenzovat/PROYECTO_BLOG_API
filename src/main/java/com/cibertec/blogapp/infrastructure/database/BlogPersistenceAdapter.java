package com.cibertec.blogapp.infrastructure.database;

import com.cibertec.blogapp.domain.model.Blog;
import com.cibertec.blogapp.domain.services.BlogPersistencePort;
import com.cibertec.blogapp.infrastructure.database.entities.BlogEntity;
import com.cibertec.blogapp.infrastructure.database.respositories.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BlogPersistenceAdapter implements BlogPersistencePort {

    private final BlogRepository repository;

    @Override
    public Blog save(Blog blog) {

        BlogEntity entity = new BlogEntity();
        entity.setTitle(blog.getTitle());
        entity.setContent(blog.getContent());
        entity.setAuthorUsername(blog.getAuthorUsername());
        entity.setCreatedAt(blog.getCreatedAt());

        BlogEntity saved = repository.save(entity);

        return new Blog(
                saved.getId(),
                saved.getTitle(),
                saved.getContent(),
                saved.getAuthorUsername(),
                saved.getCreatedAt()
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
                        e.getCreatedAt()
                ));
    }

    @Override
    public List<Blog> findAll() {
        return repository.findAll()
                .stream()
                .map(e -> new Blog(
                        e.getId(),
                        e.getTitle(),
                        e.getContent(),
                        e.getAuthorUsername(),
                        e.getCreatedAt()
                ))
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
