package org.example.first_hometask.request.Book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.first_hometask.model.BookId;
import org.example.first_hometask.model.UserId;

@AllArgsConstructor
@Data
@Schema(description = "Модель запроса на частичное обновление книги")
public class BookPatchRequest {
  @Schema(description = "Уникальный идентификатор книги")
  private BookId id;

  @Size(max = 50)
  @Schema(description = "Название книги", example = "Одиннадцать стульев")
  private String title;

  @Schema(description = "ID пользователя, связанного с книгой")
  private UserId userId;
}
