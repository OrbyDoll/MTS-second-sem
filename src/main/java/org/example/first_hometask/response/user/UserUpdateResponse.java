package org.example.first_hometask.response.user;

import lombok.Data;
import org.example.first_hometask.model.User;
import org.example.first_hometask.model.UserBook;
import org.example.first_hometask.response.book.UserBookResponse;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserUpdateResponse {
  private Long id;
  private String firstName;
  private String secondName;
  private Integer age;
  private List<UserBookResponse> books;

  public UserUpdateResponse(User user) {
    this.id = user.getId();
    this.firstName = user.getFirstName();
    this.secondName = user.getSecondName();
    this.age = user.getAge();
    List<UserBookResponse> userBooks = new ArrayList<>();
    for (UserBook book : user.getBooks()) {
      userBooks.add(new UserBookResponse(book.getId(), book.getTitle(), book.getUserId()));
    }
    this.books = userBooks;
  }
}
