package org.example.first_hometask.exception;

import org.example.first_hometask.model.UserId;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(UserId userId) {
    super("Пользователь с ID " + userId.getUserId() + " не найден");
  }
}
