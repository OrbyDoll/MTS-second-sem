package org.example.first_hometask.Repository;

import org.example.first_hometask.Model.UserUniversity;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserUniversityRepositoryImpl implements UserUniversityRepository {
  private final Map<Long, UserUniversity> universities = new HashMap<>();
  private final AtomicLong idCounter = new AtomicLong(1);

  @Override
  public List<UserUniversity> findAll() {
    return new ArrayList<>(universities.values());
  }

  @Override
  public List<UserUniversity> findByUserId(Long userId) {
    return universities.values().stream()
        .filter(u -> u.getUserId().equals(userId))
        .toList();
  }

  @Override
  public void save(UserUniversity university) {
    if (university.getId() == null) {
      university.setId(idCounter.getAndIncrement());
    }
    universities.put(university.getId(), university);
  }
}

