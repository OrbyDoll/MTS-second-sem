package org.example.first_hometask.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "user_books")
@Schema(name = "UserBook", description = "Модель книги пользователя")
public class UserBook {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  @Schema(description = "Уникальный идентификатор книги")
  private Long id;

  @Setter
  @Size(min = 1, max = 50)
  @Column(name = "title", length = 50, nullable = false)
  @Schema(description = "Название книги", example = "Одиннадцать стульев")
  private String title;

  @Setter
  @Column(name = "user_id")
  @Schema(description = "ID пользователя, связанного с книгой")
  private Long userId;

  public UserBook(String title, Long userId) {
    this.title = title;
    this.userId = userId;
  }
}
