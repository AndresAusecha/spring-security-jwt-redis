package com.aamsis.springsecuritypractice.user_management;

import com.aamsis.springsecuritypractice.user.User;
import com.aamsis.springsecuritypractice.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserManagementService {
  private final UserRepository repository;
  List<User> listUsers() {
      return repository.findAll();
  }
}
