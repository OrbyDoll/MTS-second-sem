package org.example.first_hometask.Repositories;

import org.example.first_hometask.Objects.UserBook;
import java.util.List;

public interface UserBookRepository {
  List<UserBook> findAll();
  List<UserBook> findByUserId(Long userId);
  void save(UserBook book);
}
