package org.example.first_hometask.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.first_hometask.exception.UserNotFoundException;
import org.example.first_hometask.model.User;
import org.example.first_hometask.model.UserId;
import org.example.first_hometask.repository.UsersRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class UsersService {
  private final UsersRepository userRepository;

  @Cacheable("users")
  public List<User> getAllUsers() {
    log.info("Получение всех пользователей");
    return userRepository.findAll();
  }

  @Cacheable(value = "user", key = "#userId.toString()")
  public User getUserById(UserId userId) {
    log.info("Получение пользователя с ID: {}", userId.toString());
    return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
  }

  @CacheEvict(value = "users", allEntries = true)
  public UserId createUser(User user) {
    log.info("Создание пользователя: {}", user.toString());
    return userRepository.create(user);
  }

  @CachePut(value = "user", key = "#userId.toString()")
  public User updateUser(UserId userId, User user) {
    log.info("Обновление(put) пользователя с ID: {}", userId.toString());
    return userRepository.findById(userId).map(desiredUser -> {
      desiredUser.setFirstName(user.getFirstName());
      desiredUser.setSecondName(user.getSecondName());
      desiredUser.setAge(user.getAge());
      return desiredUser;
    }).orElseThrow(() -> new UserNotFoundException(userId));
  }

  @CachePut(value = "user", key = "#userId.toString()")
  public User patchUser(UserId userId, User user) {
    log.info("Обновление(patch) пользователя с ID: {}", userId.toString());
    return userRepository.findById(userId).map(desiredUser -> {
      desiredUser.setFirstName(user.getFirstName().isBlank() ? desiredUser.getFirstName() : user.getFirstName());
      desiredUser.setSecondName(user.getSecondName().isBlank() ? desiredUser.getSecondName() : user.getSecondName());
      desiredUser.setAge(user.getAge() == 0 ? desiredUser.getAge() : user.getAge());
      return desiredUser;
    }).orElseThrow(() -> new UserNotFoundException(userId));
  }

  @CacheEvict(value = "user", key = "#userId.toString()")
  public void deleteUser(UserId userId) {
    log.info("Удаление пользователя с ID: {}", userId.toString());
    userRepository.deleteById(userId);
  }
}
