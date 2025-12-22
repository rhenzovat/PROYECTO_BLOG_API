package com.cibertec.blogapp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
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

