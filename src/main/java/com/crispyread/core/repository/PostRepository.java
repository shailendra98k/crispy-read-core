package com.crispyread.core.repository;

import com.crispyread.core.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
