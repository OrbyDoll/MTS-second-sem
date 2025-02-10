package org.example.first_hometask.Services;

import lombok.extern.slf4j.Slf4j;
import org.example.first_hometask.Objects.User;
import org.example.first_hometask.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getAllUsers() {
    log.info("Fetching all users");
    return userRepository.findAll();
  }
}
