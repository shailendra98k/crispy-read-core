package com.crispyread.core.controllers;

import com.crispyread.core.dto.CreatePostRequest;
import com.crispyread.core.dto.ErrorDetails;
import com.crispyread.core.dto.UpdatePostRequest;
import com.crispyread.core.entities.Post;
import com.crispyread.core.services.PostService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * Creates Post
     */
    @PostMapping(path = "/api/post")
    public Post createPost(@RequestBody @Valid CreatePostRequest body){
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) auth.getPrincipal();

        return this.postService.createPost(body, user.getUsername());
    }


    @PostMapping(path = "/api/posts/{action}")
    public ResponseEntity<?> publishPost(@RequestBody @Valid List<Integer> ids,
                                                  @PathVariable(name = "action") String action) {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) auth.getPrincipal();

        if (action.equals("publish")) {
            return ResponseEntity.ok(postService.publishPosts(ids, user.getUsername()));
        }
        if (action.equals("hide")) {
            return ResponseEntity.ok(postService.hidePosts(ids, user.getUsername()));
        }
        return ResponseEntity.badRequest().body(ErrorDetails.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errorMessage("Invalid action provided")
                .build());

    }

    /**
     * Update post
     */
    @PutMapping(path = "/api/post")
    public Post updatePost(
            @RequestBody @Valid UpdatePostRequest body) {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        return  this.postService.updatePost(body, user.getUsername());
    }

    /**
     * Get single post by id and slug
     */
    @GetMapping(path = "/api/post/{id}/{slug}")
    public Post getPostByIdAndSlug(
            @PathVariable(name = "id") Integer id,
            @PathVariable(name = "slug") String slug) {
        return  this.postService.getPostById(id);
    }

    /**
     * Get single post by slug
     */
    @GetMapping(path = "/api/post/{slug}")
    public Post getPostBySlug(
            @PathVariable(name = "slug") String slug) {
        return  this.postService.getPostBySlug(slug);
    }

    /**
     * Get multiple posts sorted by SORT and paginated
     */
    @GetMapping(path = "/api/posts")
    public List<Post> getPosts(
            @Nullable @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @Nullable @RequestParam(value = "size", defaultValue = "10") Integer pageSize,
            @Nullable @RequestParam(value = "isPublished", defaultValue = "true") Boolean isPublished,
            @Nullable @RequestParam(value = "sort", defaultValue = "createdAt:DESC") String sortKey) {
        Page<Post> page = this.postService.getPosts(Integer.valueOf(pageNumber), Integer.valueOf(pageSize), isPublished,  sortKey);
        return page.getContent();
    }

    /**
     * Get multiple posts filtered by category and sorted by SORT and paginated
     */
    @GetMapping(path = "/api/posts/{category}")
    public List<Post> getPostsByCategory(
            @PathVariable(name = "category") String category,
            @Nullable @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @Nullable @RequestParam(value = "size", defaultValue = "10") Integer pageSize,
            @Nullable @RequestParam(value = "isPublished", defaultValue = "true") Boolean isPublished,
            @Nullable @RequestParam(value = "sort", defaultValue = "createdAt:DESC") String sortKey) {
        Page<Post> page = this.postService.getPostsByCategory(category, Integer.valueOf(pageNumber), Integer.valueOf(pageSize), isPublished, sortKey);
        return page.getContent();
    }

    /**
     * Get featured posts
     */
    @GetMapping(path = "/api/posts/featured")
    public List<Post> getFeaturedPosts(
            @Nullable @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @Nullable @RequestParam(value = "size", defaultValue = "10") Integer pageSize,
            @Nullable @RequestParam(value = "sort", defaultValue = "createdAt:DESC") String sortKey) {
        Page <Post> page =  this.postService.getFeaturedPosts(Integer.valueOf(pageNumber), Integer.valueOf(pageSize), sortKey);
        return  page.getContent();
    }
}
