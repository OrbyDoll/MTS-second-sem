package org.example.first_hometask.Repository.Interface;

import org.example.first_hometask.Model.User;
import org.example.first_hometask.Model.UserId;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
  List<User> findAll();
  Optional<User> findById(UserId userId);
  UserId create(User user);
  void deleteById(UserId userId);
}

