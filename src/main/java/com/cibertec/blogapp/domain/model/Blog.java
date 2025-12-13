package com.cibertec.blogapp.domain.model;

import java.time.LocalDateTime;

public class Blog {

    private Long id;
    private String title;
    private String content;
    private String authorUsername;
    private LocalDateTime createdAt;

    public Blog(
            Long id,
            String title,
            String content,
            String authorUsername,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorUsername = authorUsername;
        this.createdAt = createdAt;
    }

    // getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthorUsername() { return authorUsername; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}

