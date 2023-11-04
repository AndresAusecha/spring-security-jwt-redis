package com.aamsis.springsecuritypractice.config;

import com.aamsis.springsecuritypractice.cache.Token;
import com.aamsis.springsecuritypractice.cache.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        Iterator it = request.getHeaderNames().asIterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        final String authHeader = request.getHeader("authorization");
        final String jwt = authHeader.substring(7);

        if (tokenService.isTokenExpired(jwt)) {
            throw new ServletException("Token has expired");
        }

        Token token = tokenService.getToken(jwt);
        if (token == null) {
            throw new ServletException("The token is invalid");
        }


       UserDetails userDetails = this.userDetailsService.loadUserByUsername(token.getUsername());

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("Null authentication");
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }




        System.out.println("Continue filter");
        filterChain.doFilter(request, response);
    }
}
