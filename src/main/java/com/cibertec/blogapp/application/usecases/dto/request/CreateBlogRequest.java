package com.cibertec.blogapp.application.usecases.dto.request;

import com.cibertec.blogapp.domain.model.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBlogRequest {
    private String title;
    private String content;
    private Category category;
}

