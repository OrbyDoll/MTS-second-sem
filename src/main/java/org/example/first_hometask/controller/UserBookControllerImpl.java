package org.example.first_hometask.controller;

import lombok.AllArgsConstructor;
import org.example.first_hometask.model.UserBook;
import org.example.first_hometask.model.BookId;
import org.example.first_hometask.request.Book.BookCreateRequest;
import org.example.first_hometask.request.Book.BookPatchRequest;
import org.example.first_hometask.request.Book.BookPutRequest;
import org.example.first_hometask.service.UserBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserBookControllerImpl implements UserBookController {
  private final UserBookService userBookService;

  @Override
  public ResponseEntity<List<UserBook>> getAllBooks() {
    return ResponseEntity.ok(userBookService.getAllBooks());
  }

  @Override
  public ResponseEntity<UserBook> getBookById(BookId id) {
    return ResponseEntity.ok(userBookService.getBookById(id));
  }

  @Override
  public ResponseEntity<BookId> createBook(BookCreateRequest book) {
    UserBook castedBook = new UserBook(book.getId(), book.getTitle(), book.getUserId());
    return ResponseEntity.status(HttpStatus.CREATED).body(userBookService.createBook(castedBook));
  }

  @Override
  public ResponseEntity<UserBook> updateBook(BookId id, BookPutRequest book) {
    UserBook castedBook = new UserBook(book.getId(), book.getTitle(), book.getUserId());
    return ResponseEntity.ok(userBookService.updateBook(id, castedBook));
  }

  @Override
  public ResponseEntity<UserBook> patchBook(BookId id, BookPatchRequest book) {
    UserBook castedBook = new UserBook(book.getId(), book.getTitle(), book.getUserId());
    return ResponseEntity.ok(userBookService.patchBook(id, castedBook));
  }

  @Override
  public ResponseEntity<Void> deleteBook(BookId id) {
    userBookService.deleteBook(id);
    return ResponseEntity.noContent().build();
  }
}