package com.cibertec.blogapp.application.usecases.dto.request;

import lombok.Getter;

@Getter
public class CreateBlogRequest {
    private String title;
    private String content;
}

