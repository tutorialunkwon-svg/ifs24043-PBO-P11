package org.delcom.app.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.delcom.app.entities.AuthToken;
import org.delcom.app.entities.User;
import org.delcom.app.services.AuthTokenService;
import org.delcom.app.services.UserService;
import org.delcom.app.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthContext authContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        // Skip auth endpoints and public temperature convert
        if (path.startsWith("/api/auth") || path.equals("/api/temperature/convert")) {
            filterChain.doFilter(request, response);
            return;
        }

        String raw = request.getHeader("Authorization");
        String token = null;
        if (raw != null && raw.startsWith("Bearer ")) {
            token = raw.substring(7);
        }

        if (token != null && JwtUtil.validateToken(token, true)) {
            var userId = JwtUtil.extractUserId(token);
            if (userId != null) {
                AuthToken authToken = authTokenService.findUserToken(userId, token);
                if (authToken != null) {
                    User user = userService.getUserById(authToken.getUserId());
                    if (user != null) {
                        var authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
                        var auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        authContext.setAuthUser(user);
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
