package com.cibertec.blogapp.infrastructure.database.respositories;

import com.cibertec.blogapp.infrastructure.database.entities.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
}
