package com.crispyread.core.services;

import com.crispyread.core.entities.Post;
import com.crispyread.core.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;

@Component
public class PostService {
    private PostRepository postRepository;
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * Creates a post object
     * @param body
     * @return post
     */
    public Post createPost(Post body) {
        Post post = Post.builder()
                .title(body.getTitle())
                .category(body.getCategory())
                .slug(body.getSlug())
                .content(body.getContent())
                .coverImage(body.getCoverImage())
                .seoDescription(body.getSeoDescription())
                .isFeatured(body.isFeatured())
                .isEditorPicked(body.isEditorPicked())
                .isMostPopular(body.isMostPopular())
                .isPublished(body.isPublished())
                .createdAt(new Date())
                .build();
        this.postRepository.save(post);
        return post;
    }

    /**
     * Get Post by id and slug
     * @param id
     * @param slug
     * @return
     */
    public Post getPostByIdAndSlug(Integer id, String slug ) {
        return postRepository.findPostByIdAndSlug(id,slug);
    }

    /**
     * Returns multiple posts
     * @param pageNumber
     * @param pageSize
     * @param sortKey
     * @return
     */
    public Page<Post> getPosts(Integer pageNumber, Integer pageSize, String sortKey ) {
        String[] sortKeys =  sortKey.split(":");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.valueOf(sortKeys[1]), sortKeys[0]);
        return postRepository.findAll(pageable);
    }

    /**
     * Returns multiple post by category
     * @param category
     * @param pageNumber
     * @param pageSize
     * @param sortKey
     * @return
     */
    public Page<Post> getPostsByCategory(String category, Integer pageNumber, Integer pageSize, String sortKey ) {
        String[] sortKeys =  sortKey.split(":");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.valueOf(sortKeys[1]), sortKeys[0]);
        return postRepository.findPostsByCategory(category,pageable);
    }



}
