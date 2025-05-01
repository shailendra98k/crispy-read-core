package com.crispyread.core.services;

import com.crispyread.core.dto.UserDetail;
import com.crispyread.core.enums.Role;
import com.crispyread.core.entities.User;
import com.crispyread.core.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User body) throws BadRequestException {


        if (userRepository.findByUsernameOrEmail(body.getUsername(), body.getEmail()) != null) {
            throw new BadRequestException("User with this username or email already exists");
        }

        User user = User.builder()
                .email(body.getEmail())
                .username(body.getUsername())
                .password(passwordEncoder.encode(body.getPassword()))
                .firstName(body.getFirstName())
                .lastName(body.getLastName())
                .isActive(true)
                .role(Role.USER)
                .createdAt(new Date())
                .lastModifiedAt(new Date())
                .build();
        return userRepository.save(user);
    }

    public UserDetail getUser(String username) throws BadRequestException {
        if (username == null || username.isEmpty()) {
            throw new BadRequestException("Username cannot be null or empty");
        }
        User user = userRepository.findByUsernameOrEmail(username, null);

        if (user == null) {
            throw new BadRequestException("User not found");
        }

        return UserDetail.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .isActive(user.isActive())
                .createdAt(user.getCreatedAt())
                .lastModifiedAt(user.getLastModifiedAt())
                .build();
    }
}