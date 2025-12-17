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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id", nullable = false)
    private BlogEntity blog;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String authorUsername;

    private LocalDateTime createdAt;
}
