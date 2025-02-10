package org.example.first_hometask.Controller;

import org.example.first_hometask.Service.UserCourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class UserCourseController {
  private final UserCourseService userCourseService;

  public UserCourseController(UserCourseService userCourseService) {
    this.userCourseService = userCourseService;
  }

  @GetMapping
  public String getAllCourses() {
    userCourseService.getAllCourses();
    return "Imagine that this is a list of all courses";
  }
}