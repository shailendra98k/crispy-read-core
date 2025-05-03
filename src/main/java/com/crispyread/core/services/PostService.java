package com.crispyread.core.services;

import com.crispyread.core.dto.CreatePostRequest;
import com.crispyread.core.entities.Category;
import com.crispyread.core.entities.Post;
import com.crispyread.core.entities.User;
import com.crispyread.core.repository.CategoryRepository;
import com.crispyread.core.repository.PostRepository;
import com.crispyread.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Creates a post object
     */
    public Post createPost(CreatePostRequest body, String username) {

        User user = userRepository.findByUsernameOrEmail(username, null);
        Category category = categoryRepository.findCategoryByName(body.getCategory());
        String slug = body.getTitle().toLowerCase().replaceAll(" ", "-");
        Post post = Post.builder()
                .title(body.getTitle())
                .category(category)
                .author(user)
                .slug(slug)
                .content(body.getContent())
                .coverImage(body.getCoverImage())
                .seoDescription(body.getSeoDescription())
                .isFeatured(false)
                .isEditorPicked(false)
                .isMostPopular(false)
                .isPublished(false)
                .createdAt(new Date())
                .build();
        this.postRepository.save(post);
        return post;
    }

    /**
     * Get Post by id and slug
     */
    public Post getPostByIdAndSlug(Integer id, String slug ) {
        return postRepository.findPostByIdAndSlug(id,slug);
    }


    /**
     * Get Post by slug
     */
    public Post getPostBySlug( String slug ) {
        return postRepository.findPostBySlug(slug);
    }

    /**
     * Returns multiple posts
     */
    public Page<Post> getPosts(Integer pageNumber, Integer pageSize, Boolean isPublished, String sortKey ) {
        String[] sortKeys =  sortKey.split(":");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.valueOf(sortKeys[1]), sortKeys[0]);
        return postRepository.findAllPostsByIsPublished(isPublished, pageable);
    }

    /**
     * Returns multiple post by category
     */
    public Page<Post> getPostsByCategory(String category, Integer pageNumber, Integer pageSize,Boolean isPublished,  String sortKey ) {
        String[] sortKeys =  sortKey.split(":");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.valueOf(sortKeys[1]), sortKeys[0]);
        return postRepository.findPostsByCategoryAndIsPublished(category,isPublished,pageable);
    }

    /**
     * Returns featured post(s)
     */
    public Page<Post> getFeaturedPosts(Integer pageNumber, Integer pageSize, String sortKey ) {
        String[] sortKeys =  sortKey.split(":");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.valueOf(sortKeys[1]), sortKeys[0]);
        return postRepository.findPostsByIsFeatured(true, pageable);
    }

    /**
     * Update post
     */
    public Post updatePost (Post body){

        Post post = Post.builder()
                .id(body.getId())
                .title(body.getTitle())
                .category(body.getCategory())
                .author(body.getAuthor())
                .slug(body.getSlug())
                .content(body.getContent())
                .coverImage(body.getCoverImage())
                .seoDescription(body.getSeoDescription())
                .isFeatured(body.isFeatured())
                .isEditorPicked(body.isEditorPicked())
                .isMostPopular(body.isMostPopular())
                .isPublished(body.isPublished())
                .createdAt(body.getCreatedAt())
                .lastModifiedAt(new Date())
                .build();
        this.postRepository.save(post);
        return post;
    }


    public List<Post> publishPosts(List<Integer> ids, String username) {

        User author = userRepository.findByUsernameOrEmail(username, null);
        List<Post> posts = new ArrayList<>();
        ids.forEach(id -> {
            Post post = postRepository.findPostByIdAndAuthor(id, author);
            if (post != null) {
                post.setPublished(true);
                post.setLastModifiedAt(new Date());
                postRepository.save(post);
                posts.add(post);
            }
        });
        return posts;
    }

    public List<Post> hidePosts(List<Integer> ids, String username) {

        User author = userRepository.findByUsernameOrEmail(username, null);
        List<Post> posts = new ArrayList<>();
        ids.forEach(id -> {
            Post post = postRepository.findPostByIdAndAuthor(id, author);
            if (post != null) {
                post.setPublished(false);
                post.setLastModifiedAt(new Date());
                postRepository.save(post);
                posts.add(post);
            }
        });
        return posts;
    }
}
