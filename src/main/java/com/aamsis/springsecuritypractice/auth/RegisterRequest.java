package com.aamsis.springsecuritypractice.auth;

import com.aamsis.springsecuritypractice.user.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private Role role;
}
