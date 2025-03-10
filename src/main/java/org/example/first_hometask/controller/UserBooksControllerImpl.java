package org.example.first_hometask.controller;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.AllArgsConstructor;
import org.example.first_hometask.model.UserBook;
import org.example.first_hometask.model.BookId;
import org.example.first_hometask.request.Book.BookCreateRequest;
import org.example.first_hometask.request.Book.BookPatchRequest;
import org.example.first_hometask.request.Book.BookPutRequest;
import org.example.first_hometask.service.UserBooksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@AllArgsConstructor
public class UserBooksControllerImpl implements UserBooksController {
  private final UserBooksService userBookService;
  private final RateLimiter rateLimiter = RateLimiter.ofDefaults("controller");
  private final CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("controller");

  @Override
  public CompletableFuture<ResponseEntity<List<UserBook>>> getAllBooks() {
    return circuitBreaker.executeSupplier(() -> {
      return rateLimiter.executeSupplier(() -> {
        return userBookService.getAllBooks()
            .thenApply(ResponseEntity::ok)
            .exceptionally(ex -> {
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        });
      });
    });
  }

  @Override
  public ResponseEntity<UserBook> getBookById(BookId id) {
    return circuitBreaker.executeSupplier(() -> {
      return rateLimiter.executeSupplier(() -> {
        return ResponseEntity.ok(userBookService.getBookById(id));
      });
    });
  }

  @Override
  public ResponseEntity<BookId> createBook(BookCreateRequest book) {
    return circuitBreaker.executeSupplier(() -> {
      return rateLimiter.executeSupplier(() -> {
        UserBook castedBook = new UserBook(book.getId(), book.getTitle(), book.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(userBookService.createBook(castedBook));
      });
    });
  }

  @Override
  public ResponseEntity<UserBook> updateBook(BookId id, BookPutRequest book) {
    return circuitBreaker.executeSupplier(() -> {
      return rateLimiter.executeSupplier(() -> {
        UserBook castedBook = new UserBook(book.getId(), book.getTitle(), book.getUserId());
        return ResponseEntity.ok(userBookService.updateBook(id, castedBook));
      });
    });
  }

  @Override
  public ResponseEntity<UserBook> patchBook(BookId id, BookPatchRequest book) {
    return circuitBreaker.executeSupplier(() -> {
      return rateLimiter.executeSupplier(() -> {
        UserBook castedBook = new UserBook(book.getId(), book.getTitle(), book.getUserId());
        return ResponseEntity.ok(userBookService.patchBook(id, castedBook));
      });
    });
  }

  @Override
  public ResponseEntity<Void> deleteBook(BookId id) {
    return circuitBreaker.executeSupplier(() -> {
      return rateLimiter.executeSupplier(() -> {
        userBookService.deleteBook(id);
        return ResponseEntity.noContent().build();
      });
    });
  }
}