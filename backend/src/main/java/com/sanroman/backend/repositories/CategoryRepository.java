package com.sanroman.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sanroman.backend.model.Category;


public interface CategoryRepository extends JpaRepository<Category, Long>{
    public Category findByCategory(String category);

    @Query("""
        SELECT DISTINCT c
        FROM Category c
        JOIN c.notes n
    """)
    List<Category> findAllUsedCategories();
}
