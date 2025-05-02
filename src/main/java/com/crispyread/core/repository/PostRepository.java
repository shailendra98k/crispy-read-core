package com.crispyread.core.repository;

import com.crispyread.core.entities.Post;
import com.crispyread.core.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findPostsByCategoryAndIsPublished(String category, boolean isPublished, Pageable page);
    Post findPostByIdAndSlug(Integer id, String slug);
    Post findPostByIdAndAuthor(Integer id, User author);
    Post findPostBySlug( String slug);
    Page<Post> findPostsByIsFeatured(boolean isFeatured, Pageable page);
    Page<Post> findAllPostsByIsPublished(boolean isPublished, Pageable page);


}
