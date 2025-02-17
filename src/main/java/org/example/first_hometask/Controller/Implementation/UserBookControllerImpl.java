package org.example.first_hometask.Controller.Implementation;

import lombok.AllArgsConstructor;
import org.example.first_hometask.Controller.Interface.UserBookController;
import org.example.first_hometask.Model.UserBook;
import org.example.first_hometask.Model.BookId;
import org.example.first_hometask.Request.Book.BookCreateRequest;
import org.example.first_hometask.Request.Book.BookPatchRequest;
import org.example.first_hometask.Request.Book.BookPutRequest;
import org.example.first_hometask.Service.UserBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/books")
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
    return ResponseEntity.ok(userBookService.createBook(castedBook));
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