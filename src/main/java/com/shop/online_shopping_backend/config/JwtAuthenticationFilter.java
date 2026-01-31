package com.shop.online_shopping_backend.config;

import com.shop.online_shopping_backend.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String path = request.getServletPath();

        if (path.startsWith("/auth/")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        logger.info("=== JWT TOKEN INSPECTION ===");
        logger.info("Token (first 50 chars): {}", token.substring(0, Math.min(50, token.length())) + "...");

        if (jwtUtil.validateToken(token)) {
            String email = jwtUtil.extractEmail(token);
            logger.info("Token is valid. Email extracted: {}", email);

            try {
                Claims claims = jwtUtil.extractAllClaims(token);
                logger.info("All token claims:");
                logger.info("  - Subject (email): {}", claims.getSubject());
                logger.info("  - Issued At: {}", claims.getIssuedAt());
                logger.info("  - Expiration: {}", claims.getExpiration());
                logger.info("  - All claim keys: {}", claims.keySet());
                
                // Log custom claims if any
                claims.forEach((key, value) -> {
                    if (!key.equals("sub") && !key.equals("iat") && !key.equals("exp")) {
                        logger.info("  - Custom claim '{}': {}", key, value);
                    }
                });
            } catch (Exception e) {
                logger.error("Error extracting claims: {}", e.getMessage());
            }

            UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(email, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("Authentication set in SecurityContext for user: {}", email);
            logger.info("Authorities set to: {}", authentication.getAuthorities());
        } else {
            logger.warn("Token validation failed for path: {}", path);
        }
        logger.info("=== END JWT INSPECTION ===");

        filterChain.doFilter(request, response);
    }
}
