package com.cibertec.blogapp.application.usecases;

import com.cibertec.blogapp.application.usecases.dto.request.CreateCommentRequest;
import com.cibertec.blogapp.application.usecases.dto.request.UpdateCommentRequest;
import com.cibertec.blogapp.application.usecases.dto.response.CommentResponse;
import java.util.List;

public interface CommentService {

    CommentResponse create(Long blogId, CreateCommentRequest request, String username);

    List<CommentResponse> findByBlog(Long blogId);

    void delete(Long commentId, String username, boolean isAdmin);

    CommentResponse updateComment(
            Long commentId,
            UpdateCommentRequest request,
            String username
    );
}
