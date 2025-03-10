package org.example.first_hometask.Repository;

import org.example.first_hometask.Model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
  List<User> findAll();
  Optional<User> findById(Long id);
  void save(User user);
}

