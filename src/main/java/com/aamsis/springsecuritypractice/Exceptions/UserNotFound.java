package com.aamsis.springsecuritypractice.Exceptions;


import jakarta.servlet.ServletException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Not found user")
@Data
@AllArgsConstructor
public class UserNotFound extends ServletException {
  String message;
}
