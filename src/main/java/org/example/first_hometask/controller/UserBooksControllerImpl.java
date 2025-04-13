package org.example.first_hometask.controller;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.AllArgsConstructor;
import org.example.first_hometask.model.UserBook;
import org.example.first_hometask.request.book.BookCreateRequest;
import org.example.first_hometask.request.book.BookPatchRequest;
import org.example.first_hometask.request.book.BookPutRequest;
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
            .thenApply(books -> {
              List<Long> booksIds = books.stream().map(UserBook::getId).toList();
              return ResponseEntity.ok()
                  .header("bookId", booksIds.toString())
                  .body(books);
            })
            .exceptionally(ex -> {
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            });
      });
    });
  }

  @Override
  public ResponseEntity<UserBook> getBookById(Long id) {
    return circuitBreaker.executeSupplier(() -> {
      return rateLimiter.executeSupplier(() -> {
        return ResponseEntity.ok()
            .header("bookId", String.valueOf(id))
            .body(userBookService.getBookById(id));
      });
    });
  }

  @Override
  public ResponseEntity<Long> createBook(BookCreateRequest book) {
    return circuitBreaker.executeSupplier(() -> {
      return rateLimiter.executeSupplier(() -> {
        UserBook castedBook = new UserBook(book.getTitle(), book.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED)
            .header("bookId", String.valueOf(castedBook.getId()))
            .body(userBookService.createBook(castedBook));
      });
    });
  }

  @Override
  public ResponseEntity<UserBook> updateBook(Long id, BookPutRequest book) {
    return circuitBreaker.executeSupplier(() -> {
      return rateLimiter.executeSupplier(() -> {
        UserBook castedBook = new UserBook(book.getTitle(), book.getUserId());
        return ResponseEntity.ok()
            .header("bookId", castedBook.getId().toString())
            .body(userBookService.updateBook(id, castedBook));
      });
    });
  }

  @Override
  public ResponseEntity<UserBook> patchBook(Long id, BookPatchRequest book) {
    return circuitBreaker.executeSupplier(() -> {
      return rateLimiter.executeSupplier(() -> {
        UserBook castedBook = new UserBook(book.getTitle(), book.getUserId());
        return ResponseEntity.ok()
            .header("bookId", String.valueOf(castedBook.getId()))
            .body(userBookService.patchBook(id, castedBook));
      });
    });
  }

  @Override
  public ResponseEntity<Void> deleteBook(Long id) {
    return circuitBreaker.executeSupplier(() -> {
      return rateLimiter.executeSupplier(() -> {
        userBookService.deleteBook(id);
        return ResponseEntity.noContent().header("bookId", id.toString()).build();
      });
    });
  }
}