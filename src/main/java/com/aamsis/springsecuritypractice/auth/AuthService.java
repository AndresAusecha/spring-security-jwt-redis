package com.aamsis.springsecuritypractice.auth;

import com.aamsis.springsecuritypractice.cache.Token;
import com.aamsis.springsecuritypractice.cache.TokenService;
import com.aamsis.springsecuritypractice.dtos.LoginDTO;
import com.aamsis.springsecuritypractice.dtos.RegisterDTO;
import com.aamsis.springsecuritypractice.user.User;
import com.aamsis.springsecuritypractice.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.ObjectInputFilter;
import java.util.Date;
import java.util.Optional;

import static org.springframework.security.config.Elements.JWT;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    RegisterDTO register(RegisterRequest req) {
      User user = User.builder()
        .username(req.getUsername())
        .email(req.getEmail())
        .role(req.getRole())
        .password(req.getPassword())
        .build();

      User savedUser = userRepository.save(user);
      String token = tokenService.buildToken(savedUser);

      tokenService.setTokenInCache(
        token,
        Token
          .builder()
          .creationDate(new Date())
          .isValid(true)
          .jwtExpiration(tokenService.extractExpiration(token))
          .userId(user.getId())
          .username(user.getUsername())
          .value(token)
          .build()
      );
      return new RegisterDTO(token);
    }

    LoginDTO login(LoginRequest req){
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          req.getUsername(),
          req.getPassword()
        )
      );
      Optional<User> userQuery = userRepository.findByUsername(req.getUsername());
      if (userQuery.isEmpty()) {
        throw new HttpClientErrorException(HttpStatusCode.valueOf(400), "User not found");
      }
      User user = userQuery.get();

      String token = tokenService.buildToken(user);

      tokenService.setTokenInCache(
              token,
              Token
                .builder()
                .creationDate(new Date())
                .isValid(true)
                .jwtExpiration(tokenService.extractExpiration(token))
                .userId(user.getId())
                .username(user.getUsername())
                .value(token)
                .build()
      );
      return new LoginDTO(token);
    }
}
