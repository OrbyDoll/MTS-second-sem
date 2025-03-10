package org.example.first_hometask.Service;

import lombok.extern.slf4j.Slf4j;
import org.example.first_hometask.Model.UserCourse;
import org.example.first_hometask.Repository.UserCourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserCourseService {
  private final UserCourseRepository userCourseRepository;

  public UserCourseService(UserCourseRepository userCourseRepository) {
    this.userCourseRepository = userCourseRepository;
  }

  public List<UserCourse> getAllCourses() {
    log.info("Fetching all user courses");
    return userCourseRepository.findAll();
  }
}
