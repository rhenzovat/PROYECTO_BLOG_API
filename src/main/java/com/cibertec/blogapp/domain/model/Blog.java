package com.cibertec.blogapp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

    private Long id;
    private String title;
    private String content;
    private String authorUsername;
    private LocalDateTime createdAt;
    private Category category;


//    public Blog(
//            Long id,
//            String title,
//            String content,
//            String authorUsername,
//            LocalDateTime createdAt,
//            Category category,
//            Long commentsCount
//    ) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//        this.authorUsername = authorUsername;
//        this.createdAt = createdAt;
//        this.category = category;
//        this.commentsCount = commentsCount;
//    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

//    // getters
//    public Long getId() { return id; }
//    public String getTitle() { return title; }
//    public String getContent() { return content; }
//    public String getAuthorUsername() { return authorUsername; }
//    public LocalDateTime getCreatedAt() { return createdAt; }
//    public Category getCategory() { return category; }




}

