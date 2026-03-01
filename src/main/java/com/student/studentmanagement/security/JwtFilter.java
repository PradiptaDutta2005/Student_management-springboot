package com.student.studentmanagement.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // ✅ 🔥 ADD THIS BLOCK AT THE VERY TOP 🔥
//        String contentType = request.getContentType();
//        if (contentType != null && contentType.startsWith("multipart/")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
        // ✅ 🔥 END OF FIX 🔥

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            try {
                // 1️⃣ Extract token
                String token = authHeader.substring(7);

                // 2️⃣ Extract username & role from token
                String username = jwtUtil.extractUsername(token);
                String role = jwtUtil.extractRole(token);

                System.out.println("ROLE FROM TOKEN = " + role);

                // 3️⃣ Create Authentication object
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + role))
                        );

                // 4️⃣ Set authentication
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        // 5️⃣ Continue filter chain
        filterChain.doFilter(request, response);
    }
}