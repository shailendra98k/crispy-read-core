package com.crispyread.core.controllers;

import com.crispyread.core.entities.Post;
import com.crispyread.core.services.PostService;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    private PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Creates Post
     */
    @PostMapping(path = "/api/post")
    public Post createPost(@RequestBody Post body){
        return this.postService.createPost(body);
    }

    /**
     * Get multiple posts sorted by SORT and paginated
     */
    @GetMapping(path = "/api/posts")
    public List<Post> getPosts(
            @Nullable @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @Nullable @RequestParam(value = "size", defaultValue = "10") Integer pageSize,
            @Nullable @RequestParam(value = "sort", defaultValue = "createdAt:DESC") String sortKey) {
        Page<Post> page = this.postService.getPosts(Integer.valueOf(pageNumber), Integer.valueOf(pageSize), sortKey);
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
            @Nullable @RequestParam(value = "sort", defaultValue = "createdAt:DESC") String sortKey) {
        Page<Post> page = this.postService.getPostsByCategory(category, Integer.valueOf(pageNumber), Integer.valueOf(pageSize), sortKey);
        return page.getContent();
    }



}
