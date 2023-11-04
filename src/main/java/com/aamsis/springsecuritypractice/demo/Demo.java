package com.aamsis.springsecuritypractice.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class Demo {
  @GetMapping
  public ResponseEntity<?> helloWorld(){
    return ResponseEntity.ok("Hello world");
  }
}
