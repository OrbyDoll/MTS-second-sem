package org.example.first_hometask.Repositories;

import org.example.first_hometask.Objects.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
  List<User> findAll();
  Optional<User> findById(Long id);
  void save(User user);
}

