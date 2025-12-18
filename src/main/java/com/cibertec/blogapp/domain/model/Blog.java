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




    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }






}

