package org.example.first_hometask.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "outbox")
@Entity
public class OutboxRecord {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Schema(description = "Уникальный идентификатор записи")
  private Long id;

  @NotNull
  @Setter
  @Schema(description = "Текст записи")
  private String data;

  public OutboxRecord(String data) {
    this.data = data;
  }
}
