package com.aamsis.springsecuritypractice.auth;

import com.aamsis.springsecuritypractice.Exceptions.UserNotFound;
import com.aamsis.springsecuritypractice.cache.Token;
import com.aamsis.springsecuritypractice.cache.TokenService;
import com.aamsis.springsecuritypractice.dtos.LoginDTO;
import com.aamsis.springsecuritypractice.dtos.RegisterDTO;
import com.aamsis.springsecuritypractice.user.User;
import com.aamsis.springsecuritypractice.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final CacheManager cacheManager;

    Logger logger = Logger.getLogger(AuthService.class.getName());

    RegisterDTO register(RegisterRequest req) {
      User user = User.builder()
        .username(req.getUsername())
        .email(req.getEmail())
        .role(req.getRole())
        .password(req.getPassword())
        .build();

      User savedUser = userRepository.save(user);
      String token = tokenService.buildToken(savedUser);

      tokenService.save(
        savedUser.getUsername(),
        Token
          .builder()
          .creationDate(new Date())
          .isValid(true)
          .jwtExpiration(tokenService.extractExpiration(token))
          .userId(user.getId())
          .username(user.getUsername())
          .value(token)
          .build()
          .toString()
      );
      return new RegisterDTO(token);
    }

    LoginDTO login(LoginRequest req) throws UserNotFound {
      Optional<User> userQuery = this.userRepository.findByUsername(req.getUsername());
      boolean isEmpty = userQuery.isEmpty();
      if (isEmpty) {
        throw new UserNotFound("User not found");
      }

      User user = userQuery.get();
      String token = tokenService.buildToken(user);
      tokenService.save(
              user.getUsername(),
              Token
                .builder()
                .creationDate(new Date())
                .isValid(true)
                .jwtExpiration(tokenService.extractExpiration(token))
                .userId(user.getId())
                .username(user.getUsername())
                .value(token)
                .build()
                .toString()
      );
      return new LoginDTO(token);
    }

    public void evictSingleCacheValue(String cacheName, String cacheKey) {
        try {
            cacheManager.getCache(cacheName) .evict(cacheKey);
        } catch(NullPointerException e) {
            logger.info("Cache name key " + cacheKey + "not found for cache name " + cacheName);
        }
    }

    public void logout(HttpServletRequest request) {
        Iterator<String> it = request.getHeaderNames().asIterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        final String authHeader = request.getHeader("authorization");
        final String jwt = authHeader.substring(7);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(tokenService.extractUsername(jwt));
        evictSingleCacheValue("tokenCache", userDetails.getUsername());
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
