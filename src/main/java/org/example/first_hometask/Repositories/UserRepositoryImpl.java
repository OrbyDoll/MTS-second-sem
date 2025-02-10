package org.example.first_hometask.Repositories;

import org.example.first_hometask.Objects.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepositoryImpl implements UserRepository {
  private final Map<Long, User> users = new ConcurrentHashMap<>();
  private final AtomicLong idCounter = new AtomicLong(1);

  @Override
  public List<User> findAll() {
    return new ArrayList<>(users.values());
  }

  @Override
  public Optional<User> findById(Long id) {
    return Optional.ofNullable(users.get(id));
  }

  @Override
  public void save(User user) {
    if (user.getId() == null) {
      user.setId(idCounter.getAndIncrement());
    }
    users.put(user.getId(), user);
  }
}

