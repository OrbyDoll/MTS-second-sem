package org.example.first_hometask.Repository;

import org.example.first_hometask.Model.UserBook;
import java.util.List;

public interface UserBookRepository {
  List<UserBook> findAll();
  List<UserBook> findByUserId(Long userId);
  void save(UserBook book);
}
