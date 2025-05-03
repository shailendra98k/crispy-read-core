package com.crispyread.core.repository;

import com.crispyread.core.entities.Category;
import com.crispyread.core.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryByName(String name);
}