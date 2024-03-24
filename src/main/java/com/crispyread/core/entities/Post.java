package com.crispyread.core.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String title;

    private String category;

    private String slug;

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
