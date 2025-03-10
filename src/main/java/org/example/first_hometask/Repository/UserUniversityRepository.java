package org.example.first_hometask.Repository;

import org.example.first_hometask.Model.UserUniversity;
import java.util.List;

public interface UserUniversityRepository {
  List<UserUniversity> findAll();
  List<UserUniversity> findByUserId(Long userId);
  void save(UserUniversity university);
}
