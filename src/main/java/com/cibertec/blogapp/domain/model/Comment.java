package com.cibertec.blogapp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private Long id;
    private Long blogId;
    private String content;
    private String authorUsername;
    private LocalDateTime createdAt;


}

