package com.cibertec.blogapp.infrastructure.database;

import com.cibertec.blogapp.domain.model.Comment;
import com.cibertec.blogapp.domain.services.CommentPersistencePort;
import com.cibertec.blogapp.infrastructure.database.entities.CommentEntity;
import com.cibertec.blogapp.infrastructure.database.respositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentPersistenceAdapter implements CommentPersistencePort {

    private final CommentRepository repository;

    @Override
    public Comment save(Comment comment) {

        CommentEntity entity = new CommentEntity();
        entity.setBlogId(comment.getBlogId());
        entity.setContent(comment.getContent());
        entity.setAuthorUsername(comment.getAuthorUsername());
        entity.setCreatedAt(comment.getCreatedAt());

        CommentEntity saved = repository.save(entity);

        return new Comment(
                saved.getId(),
                saved.getBlogId(),
                saved.getContent(),
                saved.getAuthorUsername(),
                saved.getCreatedAt()
        );
    }

    @Override
    public List<Comment> findByBlogId(Long blogId) {
        return repository.findByBlogId(blogId)
                .stream()
                .map(e -> new Comment(
                        e.getId(),
                        e.getBlogId(),
                        e.getContent(),
                        e.getAuthorUsername(),
                        e.getCreatedAt()
                ))
                .toList();
    }

    @Override
    public Comment findById(Long id) {
        return repository.findById(id)
                .map(e -> new Comment(
                        e.getId(),
                        e.getBlogId(),          // ✅ 2do
                        e.getContent(),         // ✅ 3ro
                        e.getAuthorUsername(),  // ✅ 4to
                        e.getCreatedAt()
                ))
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }


}
