package com.crispyread.core.services;

import com.crispyread.core.dto.CreatePostRequest;
import com.crispyread.core.dto.PostsResponse;
import com.crispyread.core.dto.UpdatePostRequest;
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
     * Get Post by slug
     */
    public Post getPostById( Integer id ) {
        return postRepository.findPostById(id);
    }

    /**
     * Returns multiple posts
     */
    public PostsResponse getPosts(Integer pageNumber, Integer pageSize, Boolean isPublished, String sortKey ) {
        String[] sortKeys =  sortKey.split(":");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.valueOf(sortKeys[1]), sortKeys[0]);
        Page<Post> posts =  postRepository.findPostsByIsPublished(isPublished, pageable);
        int totalPublishedPost = postRepository.countByIsPublished(isPublished);
        return PostsResponse.builder().totalPublishedPosts(totalPublishedPost).posts(posts.getContent()).build();
    }

    /**
     * Returns multiple post by category
     */
    public PostsResponse getPostsByCategory(String category, Integer pageNumber, Integer pageSize,Boolean isPublished,  String sortKey ) {
        String[] sortKeys =  sortKey.split(":");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.valueOf(sortKeys[1]), sortKeys[0]);
        Page<Post> posts =  postRepository.findPostsByCategoryAndIsPublished( Category.builder().name(category).build(), isPublished, pageable);
        int totalPublishedPost = postRepository.countByIsPublishedAndCategory(isPublished, Category.builder().name(category).build());
        return PostsResponse.builder().totalPublishedPosts(totalPublishedPost).posts(posts.getContent()).build();
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
    public Post updatePost (UpdatePostRequest body, String username) {

        Post existingPost = postRepository.findPostById(body.getId());
        User user = userRepository.findByUsernameOrEmail(username, null);
        Category category = categoryRepository.findCategoryByName(body.getCategory());
        if (existingPost == null || user == null || category == null) {
            return null;
        }
        Post updatedPost = Post.builder()
                .id(existingPost.getId())
                .title(body.getTitle())
                .slug(body.getTitle().toLowerCase().replaceAll(" ", "-"))
                .content(body.getContent())
                .category(category)
                .coverImage(body.getCoverImage())
                .seoDescription(body.getSeoDescription())
                .isFeatured(existingPost.isFeatured())
                .isEditorPicked(existingPost.isEditorPicked())
                .isMostPopular(existingPost.isMostPopular())
                .isPublished(existingPost.isPublished())
                .createdAt(existingPost.getCreatedAt())
                .lastModifiedAt(new Date())
                .author(user)
                .build();
        this.postRepository.save(updatedPost);
        return updatedPost;
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
