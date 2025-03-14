package org.example.first_hometask.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Schema(description = "Модель запроса на создание пользователя")
public class UserCreateRequest {

  @NotBlank
  @Size(min = 1, max = 50)
  @Schema(description = "Имя пользователя", example = "Антон")
  private String firstName;

  @NotBlank
  @Size(min = 1, max = 50)
  @Schema(description = "Фамилия пользователя", example = "Хазин")
  private String secondName;

  @NotNull
  @Min(value = 18)
  @Schema(description = "Возраст пользователя")
  private Integer age;
}
