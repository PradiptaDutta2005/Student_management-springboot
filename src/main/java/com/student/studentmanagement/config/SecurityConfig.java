package com.student.studentmanagement.config;

import com.student.studentmanagement.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // ❌ Disable CSRF (JWT based)
                .csrf(csrf -> csrf.disable())

                // ❌ No sessions (JWT only)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // ✅ Authorization rules
                .authorizeHttpRequests(auth -> auth

                        // 🔓 Public APIs
                        .requestMatchers("/auth/**").permitAll()

                        // 📘 Notes APIs
                        .requestMatchers("/notes/upload").permitAll()
                        .requestMatchers("/notes/subject/**")
                        .hasAnyRole("TEACHER", "STUDENT")

                        // 👑 Admin APIs
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 👨‍🏫 Teacher APIs
                        .requestMatchers("/teacher/**").hasRole("TEACHER")

                        // 🎓 Student APIs
                        .requestMatchers("/student/**").hasRole("STUDENT")

                        // 🔒 Everything else needs login
                        .anyRequest().authenticated()
                )

                // ✅ JWT filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 🔐 Password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}