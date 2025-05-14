package com.crispyread.core.filters;

import com.crispyread.core.impl.UserDetailsServiceImpl;
import com.crispyread.core.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.crispyread.core.constants.Constants.AUTHORIZATION;
import static com.crispyread.core.constants.Constants.BEARER;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            final String authorizationHeader = request.getHeader(AUTHORIZATION);

            String username = null;
            String jwt = null;

            if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
                jwt = authorizationHeader.substring(7);
            }
            final String cookieHeader = request.getHeader("Cookie");
            System.out.println("Cookie Header: " + cookieHeader);
            if (jwt == null && cookieHeader != null && cookieHeader.contains("jwtToken=")) {
                int startIndex = cookieHeader.indexOf("jwtToken=") + 9;
                int endIndex = cookieHeader.indexOf(";", startIndex);
                jwt = (endIndex > 0) ? cookieHeader.substring(startIndex, endIndex) : cookieHeader.substring(startIndex);
            }

            username = JwtUtil.extractUsername(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (JwtUtil.validateToken(jwt)) {
                    var authentication = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        } catch (Exception e) {
            System.out.println("JWT Filter Exception: " + e.getMessage());
        } finally {
            chain.doFilter(request, response);
        }
    }
}