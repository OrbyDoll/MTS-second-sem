package org.example.first_hometask.Repository.Interface;

import org.example.first_hometask.Model.UserBook;
import org.example.first_hometask.Model.BookId;

import java.util.List;
import java.util.Optional;

public interface UserBookRepository {
  List<UserBook> findAll();
  Optional<UserBook> findByBookId(BookId userBookId);
  BookId create(UserBook book);
  void deleteById(BookId userBookId);
}
