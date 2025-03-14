package org.example.first_hometask.request.book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Schema(description = "Модель запроса на частичное обновление книги")
public class BookPatchRequest {
  @Schema(description = "Уникальный идентификатор книги")
  private Long id;

  @Size(max = 50)
  @Schema(description = "Название книги", example = "Одиннадцать стульев")
  private String title;

  @Schema(description = "ID пользователя, связанного с книгой")
  private Long userId;
}
