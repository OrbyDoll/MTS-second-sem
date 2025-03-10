package org.example.first_hometask.repository;

import org.example.first_hometask.model.User;
import org.example.first_hometask.model.UserId;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UsersRepositoryImpl implements UsersRepository {
  private final Map<UserId, User> users = new ConcurrentHashMap<>();
  private final AtomicLong idCounter = new AtomicLong(1);

  @Override
  public List<User> findAll() {
    return new ArrayList<>(users.values());
  }

  @Override
  public Optional<User> findById(UserId userId) {
    return Optional.ofNullable(users.get(userId));
  }

  @Override
  public UserId create(User user) {
    UserId id = new UserId(idCounter.getAndIncrement());
    user.setId(id);
    user.setBooks(new ArrayList<>());
    users.put(user.getId(), user);
    return id;
  }

  @Override
  public void deleteById(UserId userId) {
    users.remove(userId);
  }
}

