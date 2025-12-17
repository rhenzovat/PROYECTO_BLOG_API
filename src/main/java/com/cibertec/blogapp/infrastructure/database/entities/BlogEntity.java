package com.cibertec.blogapp.infrastructure.database.entities;

import com.cibertec.blogapp.domain.model.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "blogs")
@Getter
@Setter
public class BlogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String authorUsername;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "blog", fetch = FetchType.LAZY)
    private List<CommentEntity> comments;



}

