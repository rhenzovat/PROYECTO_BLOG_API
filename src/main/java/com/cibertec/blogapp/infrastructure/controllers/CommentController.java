package com.cibertec.blogapp.infrastructure.controllers;

import com.cibertec.blogapp.application.usecases.CommentService;
import com.cibertec.blogapp.application.usecases.dto.request.CreateCommentRequest;
import com.cibertec.blogapp.application.usecases.dto.request.UpdateCommentRequest;
import com.cibertec.blogapp.application.usecases.dto.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs/{blogId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentResponse create(
            @PathVariable Long blogId,
            @RequestBody CreateCommentRequest request,
            @AuthenticationPrincipal UserDetails user
    ) {
        return commentService.create(blogId, request, user.getUsername());
    }

    @GetMapping
    public List<CommentResponse> list(@PathVariable Long blogId) {
        return commentService.findByBlog(blogId);
    }

    @DeleteMapping("/{commentId}")
    public void delete(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetails user
    ) {
        boolean isAdmin = user.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        commentService.delete(
                commentId,
                user.getUsername(),
                isAdmin
        );
    }

    @PutMapping("/{commentId}")
    public CommentResponse updateComment(
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request,
            Authentication authentication
    ) {
        String username = authentication.getName();

        return commentService.updateComment(
                commentId,
                request,
                username
        );
    }
}
