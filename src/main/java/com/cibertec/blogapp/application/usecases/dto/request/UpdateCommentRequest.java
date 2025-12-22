package com.cibertec.blogapp.application.usecases.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCommentRequest {
    private String content;
}
