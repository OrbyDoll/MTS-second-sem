package org.example.first_hometask.exception;

import org.example.first_hometask.model.BookId;

public class BookNotFoundException extends RuntimeException {
  public BookNotFoundException(BookId userBookId) {
    super("Книга с ID " + userBookId.getUserBookId() + " не найдена");
  }
}
