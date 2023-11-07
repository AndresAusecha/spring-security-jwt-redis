package com.aamsis.springsecuritypractice.user_management;

import com.aamsis.springsecuritypractice.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user-management")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor

public class UserManagement {
  private final UserManagementService service;

  @GetMapping("/list")
  public List<User> fetchUsers(){
    return service.listUsers();
  }
}
