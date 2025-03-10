package org.example.first_hometask.repository;

import org.example.first_hometask.model.User;
import org.example.first_hometask.model.UserId;

import java.util.List;
import java.util.Optional;

public interface UsersRepository {
  List<User> findAll();
  Optional<User> findById(UserId userId);
  UserId create(User user);
  void deleteById(UserId userId);
}

