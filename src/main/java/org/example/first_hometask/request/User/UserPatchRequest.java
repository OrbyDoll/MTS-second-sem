package org.example.first_hometask.request.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.first_hometask.model.UserBook;
import org.example.first_hometask.model.UserId;

import java.util.List;
@AllArgsConstructor
@Data
@Schema(description = "Модель запроса на частичное обновление пользователя")
public class UserPatchRequest {
  @Schema(description = "Уникальный идентификатор пользователя")
  private UserId id;

  @Schema(description = "Имя пользователя", example = "Антон")
  @Size(max = 50)
  private String firstName;

  @Size(max = 50)
  @Schema(description = "Фамилия пользователя", example = "Хазин")
  private String secondName;

  @Min(value = 18)
  @Schema(description = "Возраст пользователя")
  private int age;

  @Schema(description = "Книги пользователя")
  private List<UserBook> books;
}

