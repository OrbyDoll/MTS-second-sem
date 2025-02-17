package org.example.first_hometask.Repository.Implementation;

import org.example.first_hometask.Model.UserBook;
import org.example.first_hometask.Model.BookId;
import org.example.first_hometask.Repository.Interface.UserBookRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserBookRepositoryImpl implements UserBookRepository {
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