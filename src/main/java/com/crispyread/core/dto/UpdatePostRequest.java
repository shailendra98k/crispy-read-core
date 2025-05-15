package com.crispyread.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePostRequest {
    @NotNull(message = "ID cannot be null")
    private Integer id;

    @NotBlank(message = "Slug cannot be null or empty")
    private String slug;

    @NotBlank(message = "Title cannot be null or empty")
    private String title;

    @NotBlank(message = "Content cannot be null or empty")
    private String content;

    @NotBlank(message = "Category cannot be null or empty")
    private String category;

    private String coverImage;
    private String seoDescription;
}