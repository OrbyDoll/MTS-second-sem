package org.example.first_hometask.repository;

import org.example.first_hometask.model.UserBook;
import org.example.first_hometask.model.BookId;

import java.util.List;
import java.util.Optional;

public interface UserBookRepository {
  List<UserBook> findAll();
  Optional<UserBook> findByBookId(BookId userBookId);
  BookId create(UserBook book);
  void deleteById(BookId userBookId);
}
