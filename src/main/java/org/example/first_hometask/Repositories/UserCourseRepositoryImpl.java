package org.example.first_hometask.Repositories;

import org.example.first_hometask.Objects.UserCourse;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserCourseRepositoryImpl implements UserCourseRepository {
  private final Map<Long, UserCourse> courses = new HashMap<>();
  private final AtomicLong idCounter = new AtomicLong(1);

  @Override
  public List<UserCourse> findAll() {
    return new ArrayList<>(courses.values());
  }

  @Override
  public List<UserCourse> findByUserId(Long userId) {
    return courses.values().stream()
        .filter(c -> c.getUserId().equals(userId))
        .toList();
  }

  @Override
  public void save(UserCourse course) {
    if (course.getId() == null) {
      course.setId(idCounter.getAndIncrement());
    }
    courses.put(course.getId(), course);
  }
}

