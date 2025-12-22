package com.cibertec.blogapp.application.usecases.impl;

import com.cibertec.blogapp.application.usecases.CommentService;
import com.cibertec.blogapp.application.usecases.dto.request.CreateCommentRequest;
import com.cibertec.blogapp.application.usecases.dto.request.UpdateCommentRequest;
import com.cibertec.blogapp.application.usecases.dto.response.CommentResponse;
import com.cibertec.blogapp.domain.model.Comment;
import com.cibertec.blogapp.domain.model.User;
import com.cibertec.blogapp.domain.services.BlogPersistencePort;
import com.cibertec.blogapp.domain.services.CommentPersistencePort;
import com.cibertec.blogapp.domain.services.UserPersistencePort;
import com.cibertec.blogapp.exception.ForbiddenException;
import com.cibertec.blogapp.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentPersistencePort commentPort;
    private final BlogPersistencePort blogPort;
    private final UserPersistencePort userPort;

    @Override
    public CommentResponse create(Long blogId, CreateCommentRequest request, String username) {

        blogPort.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog no encontrado"));

        Comment comment = new Comment(
                null,
                blogId,
                request.getContent(),
                username,
                LocalDateTime.now()
        );

        Comment saved = commentPort.save(comment);

        return new CommentResponse(
                saved.getId(),
                saved.getContent(),
                saved.getAuthorUsername(),
                saved.getCreatedAt()
        );
    }

    @Override
    public List<CommentResponse> findByBlog(Long blogId) {
        return commentPort.findByBlogId(blogId)
                .stream()
                .map(c -> new CommentResponse(
                        c.getId(),
                        c.getContent(),
                        c.getAuthorUsername(),
                        c.getCreatedAt()
                ))
                .toList();
    }

    @Override
    public void delete(
            Long commentId,
            String username,
            boolean isAdmin
    ) {

        Comment comment = commentPort.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario no encontrado"));

        boolean isAuthor = comment.getAuthorUsername().equals(username);

        if (!isAdmin && !isAuthor) {
            throw new ForbiddenException("No tienes permiso para eliminar este comentario");
        }

        commentPort.deleteById(commentId);
    }

    @Override
    public CommentResponse updateComment(
            Long commentId,
            UpdateCommentRequest request,
            String username
    ) {

        Comment comment = commentPort.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario no encontrado"));

        User user = userPort.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // 🔐 VALIDACIÓN DE PERMISOS
        boolean isAuthor = comment.getAuthorUsername().equals(username);
        boolean isAdmin = user.getRole().name().equals("ADMIN");

        if (!isAuthor && !isAdmin) {
            throw new ForbiddenException("No tienes permiso para editar este comentario");
        }

        // ✏️ ACTUALIZAR CONTENIDO
        Comment updated = new Comment(
                comment.getId(),
                comment.getBlogId(),
                request.getContent(),
                comment.getAuthorUsername(),
                comment.getCreatedAt()
        );

        Comment saved = commentPort.save(updated);

        return new CommentResponse(
                saved.getId(),
                saved.getContent(),
                saved.getAuthorUsername(),
                saved.getCreatedAt()
        );
    }
}
