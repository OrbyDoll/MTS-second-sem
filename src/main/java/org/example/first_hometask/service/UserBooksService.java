package org.example.first_hometask.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.first_hometask.exception.BookNotFoundException;
import org.example.first_hometask.exception.UserNotFoundException;
import org.example.first_hometask.model.User;
import org.example.first_hometask.model.UserBook;
import org.example.first_hometask.repository.UserBooksRepository;
import org.example.first_hometask.repository.UsersRepository;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
@Slf4j
public class UserBooksService {
  private final UserBooksRepository userBookRepository;
  private final UsersRepository userRepository;

  @Async
  public CompletableFuture<List<UserBook>> getAllBooks() {
    log.info("Получение всех книг");
    return CompletableFuture.completedFuture(userBookRepository.findAll());
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
  public UserBook getBookById(Long bookId) {
    log.info("Получение книги с ID: {}", bookId.toString());
    return userBookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public Long createBook(UserBook book) {
    log.info("Создание книги: {}", book.toString());
    for (UserBook userBook : userBookRepository.findAll()) {
      if (userBook.getTitle().equals(book.getTitle())) {
        log.info("Книга с названием {} уже существует. Возвращаем существующую книгу.", book.getTitle());
        return book.getId();
      }
    }
    userRepository.findById(book.getUserId()).map(desiredUser -> {
      List<UserBook> newBooks = desiredUser.getBooks();
      newBooks.add(book);
      desiredUser.setBooks(newBooks);
      return desiredUser;
    }).orElseThrow(() -> new UserNotFoundException(book.getUserId()));
    return userBookRepository.save(book).getId();
  }

  @Retryable(
      retryFor = {UserNotFoundException.class},
      maxAttempts = 5,
      backoff = @Backoff(delay = 10000)
  )
  @Transactional(propagation = Propagation.REQUIRED)
  public UserBook updateBook(Long bookId, UserBook book) {
    log.info("Обновление(put) книги с ID: {}", bookId.toString());
    return userBookRepository.findById(bookId).map(desiredBook -> {
      userRepository.findById(desiredBook.getUserId()).map(desiredUser -> {
        List<UserBook> newBooks = desiredUser.getBooks();
        newBooks.remove(book);
        desiredUser.setBooks(newBooks);
        return desiredUser;
      }).orElseThrow(() -> new UserNotFoundException(book.getUserId()));
      desiredBook.setUserId(book.getUserId());
      desiredBook.setTitle(book.getTitle());
      userRepository.findById(book.getUserId()).map(desiredUser -> {
        List<UserBook> newBooks = desiredUser.getBooks();
        newBooks.add(book);
        desiredUser.setBooks(newBooks);
        return desiredUser;
      }).orElseThrow(() -> new UserNotFoundException(book.getUserId()));
      return desiredBook;
    }).orElseThrow(() -> new BookNotFoundException(bookId));
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public UserBook patchBook(Long bookId, UserBook book) {
    log.info("Обновление(patch) книги с ID: {}", bookId.toString());
    return userBookRepository.findById(bookId).map(desiredBook -> {
      if (book.getUserId() != null) {
        userRepository.findById(desiredBook.getUserId()).map(desiredUser -> {
          List<UserBook> newBooks = desiredUser.getBooks();
          newBooks.remove(desiredBook);
          desiredUser.setBooks(newBooks);
          return desiredUser;
        }).orElseThrow(() -> new UserNotFoundException(book.getUserId()));
        desiredBook.setUserId(book.getUserId() == null ? desiredBook.getUserId() : book.getUserId());
        desiredBook.setTitle(book.getTitle() == null ? desiredBook.getTitle() : book.getTitle());
        userRepository.findById(book.getUserId()).map(desiredUser -> {
          List<UserBook> newBooks = desiredUser.getBooks();
          newBooks.add(desiredBook);
          desiredUser.setBooks(newBooks);
          return desiredUser;
        }).orElseThrow(() -> new UserNotFoundException(book.getUserId()));
      }
      desiredBook.setUserId(book.getUserId() == null ? desiredBook.getUserId() : book.getUserId());
      desiredBook.setTitle(book.getTitle() == null ? desiredBook.getTitle() : book.getTitle());
      return desiredBook;
    }).orElseThrow(() -> new BookNotFoundException(bookId));
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteBook(Long bookId) {
    log.info("Удаление книги с ID: {}", bookId.toString());
    UserBook desiredBook = userBookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
    User desiredUser = userRepository.findById(desiredBook.getUserId()).orElseThrow(() -> new UserNotFoundException(
        desiredBook.getUserId()));
    List<UserBook> newUserBooks = desiredUser.getBooks();
    newUserBooks.remove(desiredBook);
    desiredUser.setBooks(newUserBooks);
    userRepository.save(desiredUser);
    userBookRepository.deleteById(bookId);
  }
}