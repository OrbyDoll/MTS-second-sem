package org.example.first_hometask.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.first_hometask.exception.UserNotFoundException;
import org.example.first_hometask.model.User;
import org.example.first_hometask.repository.UsersRepository;
import org.example.first_hometask.response.user.UserGetAllResponse;
import org.example.first_hometask.response.user.UserGetResponse;
import org.example.first_hometask.response.user.UserUpdateResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class UsersService {
  private final UsersRepository userRepository;

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
  @Cacheable("users")
  public List<UserGetAllResponse> getAllUsers() {
    log.info("Получение всех пользователей");
    List<UserGetAllResponse> users = new ArrayList<>();
    for (User user : userRepository.findAll()) {
      users.add(new UserGetAllResponse(user));
    }
    return users;
  }

  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
  @Cacheable(value = "user", key = "#userId.toString()")
  public UserGetResponse getUserById(Long userId) {
    log.info("Получение пользователя с ID: {}", userId.toString());
    User desiredUser =
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    return new UserGetResponse(desiredUser);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @CacheEvict(value = "users", allEntries = true)
  public Long createUser(User user) {
    log.info("Создание пользователя: {}", user.toString());
    return userRepository.save(user).getId();
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @CachePut(value = "user", key = "#userId.toString()")
  public UserUpdateResponse updateUser(Long userId, User user) {
    log.info("Обновление(put) пользователя с ID: {}", userId.toString());
    return userRepository.findById(userId).map(desiredUser -> {
      desiredUser.setFirstName(user.getFirstName());
      desiredUser.setSecondName(user.getSecondName());
      desiredUser.setAge(user.getAge());
      User savedUser = userRepository.save(desiredUser);
      return new UserUpdateResponse(savedUser);
    }).orElseThrow(() -> new UserNotFoundException(userId));
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @CachePut(value = "user", key = "#userId.toString()")
  public UserUpdateResponse patchUser(Long userId, User user) {
    log.info("Обновление(patch) пользователя с ID: {}", userId.toString());
    return userRepository.findById(userId).map(desiredUser -> {
      if (user.getFirstName() != null && !user.getSecondName().isBlank()) {
        desiredUser.setFirstName(user.getFirstName());
      }
      if (user.getSecondName() != null && !user.getSecondName().isBlank()) {
        desiredUser.setSecondName(user.getSecondName());
      }
      if (user.getAge() != null) {
        desiredUser.setAge(user.getAge());
      }
      User savedUser = userRepository.save(desiredUser);
      return new UserUpdateResponse(savedUser);
    }).orElseThrow(() -> new UserNotFoundException(userId));
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @CacheEvict(value = "user", key = "#userId.toString()")
  public void deleteUser(Long userId) {
    log.info("Удаление пользователя с ID: {}", userId.toString());
    userRepository.deleteById(userId);
  }
}