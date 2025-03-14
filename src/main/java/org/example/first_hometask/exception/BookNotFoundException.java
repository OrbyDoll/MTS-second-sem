package org.example.first_hometask.exception;

public class BookNotFoundException extends RuntimeException {
  public BookNotFoundException(Long userBookId) {
    super("Книга с ID " + userBookId + " не найдена");
  }
}
