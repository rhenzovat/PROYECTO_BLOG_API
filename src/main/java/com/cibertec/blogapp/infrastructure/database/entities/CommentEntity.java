package com.cibertec.blogapp.infrastructure.database.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long blogId;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String authorUsername;

    private LocalDateTime createdAt;
}
