package com.cibertec.blogapp.application.usecases.dto.response;

import com.cibertec.blogapp.domain.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BlogHomeResponse {

    private Long id;
    private String title;
    private String author;
    private Category category;
    private LocalDateTime createdAt;
    private Long totalComments;
}
