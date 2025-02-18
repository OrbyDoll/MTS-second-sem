package org.example.first_hometask.model;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookId {
  @Positive
  private long userBookId;

  public BookId(String stringBookId) {
    this.userBookId = Long.parseLong(stringBookId);
  }
}