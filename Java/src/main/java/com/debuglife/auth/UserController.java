package com.debuglife.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class UserController {

  @Autowired
  private MySQLUserDetailsService userService;

  @PostMapping("/register")
  public ResponseEntity<User> register(@RequestBody User newUser) {
    userService.Save(newUser);
    return ResponseEntity.ok(newUser);
  }
}