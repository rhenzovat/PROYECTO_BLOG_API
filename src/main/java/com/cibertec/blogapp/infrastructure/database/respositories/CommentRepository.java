package com.cibertec.blogapp.infrastructure.database.respositories;

import com.cibertec.blogapp.infrastructure.database.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findByBlogId(Long blogId);


}
