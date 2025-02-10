package org.example.first_hometask.Repositories;

import org.example.first_hometask.Objects.UserBook;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserBookRepositoryImpl implements UserBookRepository {
  private final Map<Long, UserBook> books = new HashMap<>();
  private final AtomicLong idCounter = new AtomicLong(1);

  @Override
  public List<UserBook> findAll() {
    return new ArrayList<>(books.values());
  }

  @Override
  public List<UserBook> findByUserId(Long userId) {
    return books.values().stream().filter(b -> b.getUserId().equals(userId)).toList();
  }

  @Override
  public void save(UserBook book) {
    if (book.getId() == null) {
      book.setId(idCounter.getAndIncrement());
    }
    books.put(book.getId(), book);
  }
}

