package com.crispyread.core.repository;

import com.crispyread.core.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findPostsByCategory(String category, Pageable page);
    Post findPostByIdAndSlug(Integer id, String slug);
    Page<Post> findPostsByIsFeatured(boolean isFeatured, Pageable page);
}
