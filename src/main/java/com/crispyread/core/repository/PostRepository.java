package com.crispyread.core.repository;

import com.crispyread.core.entities.Category;
import com.crispyread.core.entities.Post;
import com.crispyread.core.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findPostByIdAndSlug(Integer id, String slug);
    Post findPostById(Integer id);
    Post findPostByIdAndAuthor(Integer id, User author);
    Post findPostBySlug( String slug);
    Page<Post> findPostsByIsFeatured(boolean isFeatured, Pageable page);
    Page<Post> findPostsByIsPublished(boolean isPublished, Pageable page);
    int countByIsPublished(boolean isPublished);
    Page<Post> findPostsByCategoryAndIsPublished(Category category, boolean isPublished, Pageable page);
    int countByIsPublishedAndCategory(boolean isPublished, Category category);


}
