package org.example.first_hometask.Services;

import lombok.extern.slf4j.Slf4j;
import org.example.first_hometask.Objects.UserUniversity;
import org.example.first_hometask.Repositories.UserUniversityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserUniversityService {
  private final UserUniversityRepository userUniversityRepository;

  public UserUniversityService(UserUniversityRepository userUniversityRepository) {
    this.userUniversityRepository = userUniversityRepository;
  }

  public List<UserUniversity> getAllUniversities() {
    log.info("Fetching all user universities");
    return userUniversityRepository.findAll();
  }
}
