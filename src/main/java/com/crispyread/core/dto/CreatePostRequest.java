package com.crispyread.core.dto;

import com.crispyread.core.entities.Category;
import com.crispyread.core.entities.User;
import com.crispyread.core.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePostRequest {
    @NotBlank(message = "Title cannot be null or empty")
    private String title;
    @NotBlank(message = "Content cannot be null or empty")
    private String content;
    @NotBlank(message = "Category cannot be null or empty")
    private String category;
    private String coverImage;
    private String seoDescription;

}
