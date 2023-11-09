package com.aamsis.springsecuritypractice.Exceptions;

import jakarta.servlet.ServletException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Token not found")
@Data
@AllArgsConstructor
public class TokenNotFoundException extends ServletException {
  String message;
}
