package org.example.first_hometask.Repositories;

import org.example.first_hometask.Objects.UserCourse;
import java.util.List;

public interface UserCourseRepository {
  List<UserCourse> findAll();
  List<UserCourse> findByUserId(Long userId);
  void save(UserCourse course);
}

