package org.example.first_hometask.Repository;

import org.example.first_hometask.Model.UserCourse;
import java.util.List;

public interface UserCourseRepository {
  List<UserCourse> findAll();
  List<UserCourse> findByUserId(Long userId);
  void save(UserCourse course);
}

