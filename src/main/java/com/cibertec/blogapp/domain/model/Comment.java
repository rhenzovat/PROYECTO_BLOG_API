package com.cibertec.blogapp.domain.model;

import java.time.LocalDateTime;

public class Comment {

    private Long id;
    private Long blogId;
    private String content;
    private String authorUsername;
    private LocalDateTime createdAt;

    public Comment(
            Long id,
            Long blogId,
            String content,
            String authorUsername,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.blogId = blogId;
        this.content = content;
        this.authorUsername = authorUsername;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public Long getBlogId() { return blogId; }
    public String getContent() { return content; }
    public String getAuthorUsername() { return authorUsername; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}

