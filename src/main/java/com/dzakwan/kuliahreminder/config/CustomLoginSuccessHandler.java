package com.dzakwan.kuliahreminder.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        try {
            for (GrantedAuthority authority : authorities) {
                String role = authority.getAuthority();
                if ("ROLE_ADMIN".equals(role)) {
                    response.sendRedirect("/web/dashboard"); // ganti sesuai controller
                    return;
                } else if ("ROLE_USER".equals(role)) {
                    response.sendRedirect("/web/dashboard_user");
                    return;
                }
            }

            // fallback
            response.sendRedirect("/login?role_not_found");
        } catch (Exception e) {
            e.printStackTrace(); // tampilkan di console
            response.sendRedirect("/login?error=redirect");
        }
    }
}
