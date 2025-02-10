package org.example.first_hometask.Services;

import lombok.extern.slf4j.Slf4j;
import org.example.first_hometask.Objects.UserBook;
import org.example.first_hometask.Repositories.UserBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserBookService {
  private final UserBookRepository userBookRepository;

  public UserBookService(UserBookRepository userBookRepository) {
    this.userBookRepository = userBookRepository;
  }

  public List<UserBook> getAllBooks() {
    log.info("Fetching all user books");
    return userBookRepository.findAll();
  }
}
