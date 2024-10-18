package com.crispyread.core.entities;

import jakarta.persistence.*;
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
@Table(name = "spring_posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String category;

    @NotNull
    private String slug;

    @NotNull
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
