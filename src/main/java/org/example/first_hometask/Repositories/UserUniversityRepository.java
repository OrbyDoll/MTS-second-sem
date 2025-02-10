package org.example.first_hometask.Repositories;

import org.example.first_hometask.Objects.UserUniversity;
import java.util.List;

public interface UserUniversityRepository {
  List<UserUniversity> findAll();
  List<UserUniversity> findByUserId(Long userId);
  void save(UserUniversity university);
}
