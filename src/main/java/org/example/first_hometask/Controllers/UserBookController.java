package org.example.first_hometask.Controllers;

import org.example.first_hometask.Services.UserBookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class UserBookController {
  private final UserBookService userBookService;

  public UserBookController(UserBookService userBookService) {
    this.userBookService = userBookService;
  }

  @GetMapping
  public String getAllBooks() {
    userBookService.getAllBooks();
    return "Imagine that this is a list of all books";
  }
}