package com.crispyread.core.dto;

import com.crispyread.core.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostsResponse {
    private List<Post> posts;
    private long totalPublishedPosts;
}