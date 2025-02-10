package org.example.first_hometask.Controllers;

import org.example.first_hometask.Services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public String getUsers() {
    userService.getAllUsers();
    return "Imagine that this is a list of users";
  }
}