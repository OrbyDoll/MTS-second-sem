package org.example.first_hometask.request.book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Schema(description = "Модель запроса на создание книги")
public class BookCreateRequest {
  @Schema(description = "Уникальный идентификатор книги")
  private Long id;

  @NotBlank
  @Size(min = 1, max = 50)
  @Schema(description = "Название книги", example = "Одиннадцать стульев")
  private String title;

  @NotNull
  @Schema(description = "ID пользователя, связанного с книгой")
  private Long userId;
}

