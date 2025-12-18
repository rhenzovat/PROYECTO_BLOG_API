package com.cibertec.blogapp.infrastructure.database.respositories;

import com.cibertec.blogapp.application.usecases.dto.response.BlogHomeResponse;
import com.cibertec.blogapp.domain.model.Category;
import com.cibertec.blogapp.infrastructure.database.entities.BlogEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
//    // Filtro por categoría + recientes
    List<BlogEntity> findByCategoryOrderByCreatedAtDesc(Category category);
//
//    // Solo recientes
    List<BlogEntity> findAllByOrderByCreatedAtDesc();

// 🕒 Blogs recientes
@Query("""
    SELECT new com.cibertec.blogapp.application.usecases.dto.response.BlogHomeResponse(
        b.id,
        b.title,
        b.authorUsername,
        b.category,
        b.createdAt,
        COUNT(c)
    )
    FROM BlogEntity b
    LEFT JOIN b.comments c
    GROUP BY b
    ORDER BY b.createdAt DESC
""")
List<BlogHomeResponse> findRecentBlogs(Pageable pageable);

    // 💬 Blogs más comentados
    @Query("""
        SELECT new com.cibertec.blogapp.application.usecases.dto.response.BlogHomeResponse(
            b.id,
            b.title,
            b.authorUsername,
            b.category,
            b.createdAt,
            COUNT(c)
        )
        FROM BlogEntity b
        LEFT JOIN b.comments c
        GROUP BY b
        ORDER BY COUNT(c) DESC
    """)
    List<BlogHomeResponse> findMostCommentedBlogs(Pageable pageable);

    // 🏷️ Filtro por categoría
    @Query("""
        SELECT new com.cibertec.blogapp.application.usecases.dto.response.BlogHomeResponse(
            b.id,
            b.title,
            b.authorUsername,
            b.category,
            b.createdAt,
            COUNT(c)
        )
        FROM BlogEntity b
        LEFT JOIN b.comments c
        WHERE b.category = :category
        GROUP BY b
        ORDER BY b.createdAt DESC
    """)
    List<BlogHomeResponse> findHomeByCategory(@Param("category") Category category, Pageable pageable);
}
