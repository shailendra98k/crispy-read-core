package com.crispyread.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.env.Environment;
import static com.crispyread.core.constants.Constants.AUTH_TOKEN_COOKIE;

@RestController
public class AdminController {
    @Autowired Environment env;
    @GetMapping(path = "/api/admin/authenticate")
    public ResponseEntity<String> isAdminAuthenticated(){

        String auth_token_cookie = env.getProperty(AUTH_TOKEN_COOKIE);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,String.format("auth=%s;path=/;max-age:3600",auth_token_cookie))
                .build();
    }
}
