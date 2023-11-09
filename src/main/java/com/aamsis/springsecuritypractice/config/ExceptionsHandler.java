package com.aamsis.springsecuritypractice.config;

import com.aamsis.springsecuritypractice.Exceptions.TokenNotFoundException;
import com.aamsis.springsecuritypractice.Exceptions.UserNotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {
  @ExceptionHandler(TokenNotFoundException.class)
  public ResponseEntity<?> handleNotFoundException(TokenNotFoundException exception) {
    return ResponseEntity.badRequest().body(exception.getMessage());
  }

  @ExceptionHandler(UserNotFound.class)
  public ResponseEntity<?> handleUserNotFoundException(UserNotFound exception) {
    return ResponseEntity.badRequest().body(exception.getMessage());
  }
}
