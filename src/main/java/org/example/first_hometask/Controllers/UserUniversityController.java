package org.example.first_hometask.Controllers;

import org.example.first_hometask.Objects.UserUniversity;
import org.example.first_hometask.Services.UserBookService;
import org.example.first_hometask.Services.UserCourseService;
import org.example.first_hometask.Services.UserUniversityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/universities")
public class UserUniversityController {
  private final UserUniversityService userUniversityService;

  public UserUniversityController(UserUniversityService userUniversityService) {
    this.userUniversityService = userUniversityService;
  }

  @GetMapping
  public String getAllCourses() {
    userUniversityService.getAllUniversities();
    return "Imagine that this is a list of all universities";
  }
}