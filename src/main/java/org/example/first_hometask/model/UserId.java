package org.example.first_hometask.model;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserId {
  @Positive
  private long userId;

  public UserId(String stringId) {
    this.userId = Long.parseLong(stringId);
  }
}
