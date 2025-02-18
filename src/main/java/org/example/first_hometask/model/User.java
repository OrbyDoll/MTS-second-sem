package org.example.first_hometask.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Schema(description = "Модель пользователя")
public class User {
  @Schema(description = "Уникальный идентификатор пользователя")
  private UserId id;

  @NotBlank
  @Schema(description = "Имя пользователя", example = "Антон")
  @Size(min = 1, max = 50)
  private String firstName;

  @NotBlank
  @Size(min = 1, max = 50)
  @Schema(description = "Фамилия пользователя", example = "Хазин")
  private String secondName;

  @NotNull
  @Min(value = 18)
  @Schema(description = "Возраст пользователя")
  private int age;

  @Schema(description = "Книги пользователя")
  private List<UserBook> books;
}
