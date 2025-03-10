package org.example.first_hometask.repository;

import org.example.first_hometask.model.UserBook;
import org.example.first_hometask.model.BookId;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserBooksRepositoryImpl implements UserBooksRepository {
  private final Map<BookId, UserBook> userBooks = new ConcurrentHashMap<>();
  private final AtomicLong idCounter = new AtomicLong(1);

  @Override
  public List<UserBook> findAll() {
    return new ArrayList<>(userBooks.values());
  }

  @Override
  public Optional<UserBook> findByBookId(BookId userBookId) {
    return Optional.ofNullable(userBooks.get(userBookId));
  }

  @Override
  public BookId create(UserBook userBook) {
    BookId id = new BookId(idCounter.getAndIncrement());
    userBook.setId(id);
    userBooks.put(userBook.getId(), userBook);
    return id;
  }

  @Override
  public void deleteById(BookId userBookId) {
    userBooks.remove(userBookId);
  }
}