package com.crispyread.core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "username", name = "author")
    private User author;

    @NotBlank(message = "Title cannot be null or empty")
    private String title;

    @ManyToOne
    @JoinColumn(referencedColumnName = "name", name = "category")
    private Category category;

    @NotBlank(message = "Slug cannot be null or empty")
    private String slug;

    @NotBlank(message = "Content cannot be null or empty")
    @Column(columnDefinition = "TEXT")
    private String content;

    private String coverImage;

    @Column(columnDefinition = "TEXT")
    private String seoDescription;

    private boolean isFeatured;

    private boolean isPublished;

    private boolean isEditorPicked;

    private boolean isMostPopular;

    private Date createdAt;

    private Date lastModifiedAt;
}
