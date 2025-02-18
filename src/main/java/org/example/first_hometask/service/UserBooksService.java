package org.example.first_hometask.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.first_hometask.exception.BookNotFoundException;
import org.example.first_hometask.exception.UserNotFoundException;
import org.example.first_hometask.model.UserBook;
import org.example.first_hometask.model.BookId;
import org.example.first_hometask.repository.UserBooksRepository;
import org.example.first_hometask.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class UserBooksService {
  private final UserBooksRepository userBookRepository;
  private final UsersRepository userRepository;

  public List<UserBook> getAllBooks() {
    log.info("Получение всех книг");
    return userBookRepository.findAll();
  }

  public UserBook getBookById(BookId bookId) {
    log.info("Получение книги с ID: {}", bookId.toString());
    return userBookRepository.findByBookId(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
  }

  public BookId createBook(UserBook book) {
    log.info("Создание книги: {}", book.toString());
    userRepository.findById(book.getUserId()).map(desiredUser -> {
      List<UserBook> newBooks = desiredUser.getBooks();
      newBooks.add(book);
      desiredUser.setBooks(newBooks);
      return desiredUser;
    }).orElseThrow(() -> new UserNotFoundException(book.getUserId()));
    return userBookRepository.create(book);
  }

  public UserBook updateBook(BookId bookId, UserBook book) {
    log.info("Обновление(put) книги с ID: {}", bookId.toString());
    return userBookRepository.findByBookId(bookId).map(desiredBook -> {
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

  public UserBook patchBook(BookId bookId, UserBook book) {
    log.info("Обновление(patch) книги с ID: {}", bookId.toString());
    return userBookRepository.findByBookId(bookId).map(desiredBook -> {
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

  public void deleteBook(BookId bookId) {
    log.info("Удаление книги с ID: {}", bookId.toString());
    userBookRepository.deleteById(bookId);
  }
}
