package com.crispyread.core.controllers;

import com.crispyread.core.dto.ResponseDetails;
import com.crispyread.core.entities.User;
import com.crispyread.core.dto.UserLoginRequest;
import com.crispyread.core.utils.JwtUtil;
import com.crispyread.core.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(path = "/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Valid User user) throws Exception {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseDetails.builder()
                            .status(HttpStatus.CREATED)
                            .message("User with username: " + createdUser.getUsername() + " created successfully")
                            .build()
                    );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDetails.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message(e.getLocalizedMessage())
                            .build()
                    );
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {

        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequest.getUsername(),
                            userLoginRequest.getPassword()
                    )
            );
            // Generate JWT token
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = JwtUtil.generateToken(userDetails.getUsername());

            HttpHeaders headers = new HttpHeaders();
            headers.add("jwtToken", jwtToken);
            headers.add("Set-Cookie", "jwtToken=" + jwtToken + "; HttpOnly; Path=/; SameSite=Strict");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(headers)
                    .body(userService.getUser(userLoginRequest.getUsername()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ResponseDetails.builder()
                            .status(HttpStatus.UNAUTHORIZED)
                            .message(e.getLocalizedMessage())
                            .build()
                    );
        }
    }

    @GetMapping
    public ResponseEntity<?> getUserDetails() {
        try {
            Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User) auth.getPrincipal();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.getUser(user.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ResponseDetails.builder()
                            .status(HttpStatus.UNAUTHORIZED)
                            .message(e.getLocalizedMessage())
                            .build()
                    );
        }
    }
}