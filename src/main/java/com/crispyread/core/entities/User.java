package com.crispyread.core.entities;

import com.crispyread.core.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be null or empty")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Password cannot be null or empty")
    private volatile String password;

    @NotBlank(message = "First name cannot be null or empty")
    private String firstName;

    @NotBlank(message = "Last name cannot be null or empty")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be null or empty")
    @Column(unique = true, nullable = false)
    private String email;

    private boolean isActive;

    private Role role;

    private Date createdAt;

    private Date lastModifiedAt;
}