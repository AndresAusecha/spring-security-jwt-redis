package com.aamsis.springsecuritypractice.auth;

import com.aamsis.springsecuritypractice.Exceptions.UserNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        return ResponseEntity.ok(service.register(req));
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginRequest req) throws UserNotFound {
        return ResponseEntity.ok(service.login(req));
    }
}
