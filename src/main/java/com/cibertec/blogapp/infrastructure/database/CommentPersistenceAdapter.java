package com.cibertec.blogapp.infrastructure.database;

import com.cibertec.blogapp.domain.model.Comment;
import com.cibertec.blogapp.domain.services.CommentPersistencePort;
import com.cibertec.blogapp.infrastructure.database.entities.BlogEntity;
import com.cibertec.blogapp.infrastructure.database.entities.CommentEntity;
import com.cibertec.blogapp.infrastructure.database.respositories.BlogRepository;
import com.cibertec.blogapp.infrastructure.database.respositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentPersistenceAdapter implements CommentPersistencePort {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;

    @Override
    public Comment save(Comment comment) {

        BlogEntity blogEntity = blogRepository.findById(comment.getBlogId())
                .orElseThrow(() -> new RuntimeException("Blog no encontrado"));

        CommentEntity entity;

        // 🔹 SI EXISTE → UPDATE
        if (comment.getId() != null) {
            entity = commentRepository.findById(comment.getId())
                    .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));
        } else {
            // 🔹 SI NO EXISTE → CREATE
            entity = new CommentEntity();
            entity.setBlog(blogEntity);
            entity.setAuthorUsername(comment.getAuthorUsername());
            entity.setCreatedAt(comment.getCreatedAt());
        }

        // 🔹 SOLO SE ACTUALIZA EL CONTENIDO
        entity.setContent(comment.getContent());

        CommentEntity saved = commentRepository.save(entity);

        return new Comment(
                saved.getId(),
                saved.getBlog().getId(),
                saved.getContent(),
                saved.getAuthorUsername(),
                saved.getCreatedAt()
        );
    }

    @Override
    public List<Comment> findByBlogId(Long blogId) {
        return commentRepository.findByBlogId(blogId)
                .stream()
                .map(e -> new Comment(
                        e.getId(),
                        e.getBlog().getId(),
                        e.getContent(),
                        e.getAuthorUsername(),
                        e.getCreatedAt()
                ))
                .toList();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id)
                .map(e -> new Comment(
                        e.getId(),
                        e.getBlog().getId(),          // ✅ 2do
                        e.getContent(),         // ✅ 3ro
                        e.getAuthorUsername(),  // ✅ 4to
                        e.getCreatedAt()
                ));

    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }



}
