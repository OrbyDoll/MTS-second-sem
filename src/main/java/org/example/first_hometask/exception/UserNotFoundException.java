package org.example.first_hometask.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(Long userId) {
    super("Пользователь с ID " + userId + " не найден");
  }
}
