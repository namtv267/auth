/**
 * Custom filer, xử lý việc xác thực với JWT(JSON WEB TOKEN).
 * 1. Chặn các request và kiểm tra JWT có trong phần HTTP header.
 * 2. Xác thực token và thiết lập người dùng vào SecurityContext nếu hợp lệ
 * 3. Kiểm tra bypass với một số route
 */
package com.shopwebapp.auth.filter;

import com.shopwebapp.auth.configs.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.function.Function;
@Log4j2
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            @NonNull  HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        log.info("JWT Authentication Filter");
        Function<HttpServletRequest, String> jwt = httpServletRequest -> {
            String bearerToken = request.getHeader("Authorization");
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7);
            }
            log.error("JWT Authentication Filter Error");
            return null;
        };

        String jwtToken = jwt.apply(request);

    }
    /*
        private String getJwtFromRequest(
                HttpServletRequest request
        ) {
            String bearerToken = request.getHeader("Authorization");
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7);
            }
            return null;
        }
     */
}
