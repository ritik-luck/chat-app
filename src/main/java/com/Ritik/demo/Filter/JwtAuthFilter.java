package com.Ritik.demo.Filter;

import com.Ritik.demo.Utility.JWT_Util;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWT_Util jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String requestURI = request.getRequestURI();

            // Skip JWT validation for public endpoints
            if (isPublicEndpoint(requestURI)) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = null;
            if (requestURI.contains("/chat") || requestURI.contains("/ws")) {
                token = request.getParameter("token");
            } else {
                String authHeader = request.getHeader("Authorization");
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    token = authHeader.substring(7);
                }
            }

            if (token == null || token.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }

            String username = jwtUtil.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(token, userDetails)) {
                    setAuthenticationInContext(userDetails, request);
                }
            }
        } catch (ExpiredJwtException e) {
            logger.warn("JWT token has expired");
        } catch (SignatureException | UsernameNotFoundException e) {
            logger.warn("Invalid JWT token or user not found");
        } catch (Exception e) {
            logger.error("Error processing JWT token", e);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(String requestURI) {
        return requestURI.startsWith("/api/auth/") ||
               requestURI.startsWith("/static/") ||
               requestURI.equals("/") ||
               requestURI.equals("/index.html") ||
               requestURI.startsWith("/chat") ||
               requestURI.startsWith("/ws") ||
               requestURI.startsWith("/topic") ||
               requestURI.startsWith("/app");
    }

    private void setAuthenticationInContext(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}