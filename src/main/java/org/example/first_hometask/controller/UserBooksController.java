package org.example.first_hometask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.first_hometask.model.UserBook;
import org.example.first_hometask.model.BookId;
import org.example.first_hometask.request.Book.BookCreateRequest;
import org.example.first_hometask.request.Book.BookPatchRequest;
import org.example.first_hometask.request.Book.BookPutRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/api/books")
@Tag(name = "Books API", description = "Управление книгами пользователей")
public interface UserBooksController {

  @Operation(summary = "Получить все книги")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Все книги успешно получены",
          content = {@Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = UserBook.class))
          )
          })
  })
  @GetMapping("/")
  CompletableFuture<ResponseEntity<List<UserBook>>> getAllBooks();

  @Operation(summary = "Получить книгу по ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Книга успешно получена",
          content = {@Content(
              mediaType = "application/json",
              schema = @Schema(implementation = UserBook.class)
          )
          }),
      @ApiResponse(responseCode = "400", description = "Неверные данные запроса",
          content = {@Content}),
      @ApiResponse(responseCode = "404", description = "Книга не найдена",
          content = {@Content})
  })
  @GetMapping("/{id}")
  ResponseEntity<UserBook> getBookById(@Parameter(description = "ID книги") @Valid @PathVariable("id") BookId id);

  @Operation(summary = "Создание книги")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Книга успешно создана",
          content = {@Content(
              mediaType = "application/json",
              schema = @Schema(implementation = Long.class)
          )}),
      @ApiResponse(responseCode = "400", description = "Неверные данные запроса",
          content = {@Content}),
  })
  @PostMapping("/")
  ResponseEntity<BookId> createBook(@Parameter(description = "Данные о книге") @Valid @RequestBody BookCreateRequest UserBook);

  @Operation(summary = "Полное обновление данных книги")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Книга успешно обновлена",
          content = {@Content(
              mediaType = "application/json",
              schema = @Schema(implementation = UserBook.class)
          )
          }),
      @ApiResponse(responseCode = "404", description = "Книга не найдена",
          content = {@Content}),
      @ApiResponse(responseCode = "400", description = "Неверные данные запроса",
          content = {@Content})
  })
  @PutMapping("/{id}")
  ResponseEntity<UserBook> updateBook(@Parameter(description = "ID книги") @Valid @PathVariable("id") BookId id,
                                      @Parameter(description = "Данные о книге") @Valid @RequestBody BookPutRequest UserBook);

  @Operation(summary = "Частичное обновление данных книги")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Книга успешно обновлена",
          content = {@Content(
              mediaType = "application/json",
              schema = @Schema(implementation = UserBook.class)
          )
          }),
      @ApiResponse(responseCode = "404", description = "Книга не найдена",
          content = {@Content}),
      @ApiResponse(responseCode = "400", description = "Неверные данные запроса",
          content = {@Content})
  })
  @PatchMapping("/{id}")
  ResponseEntity<UserBook> patchBook(@Parameter(description = "ID книги") @Valid @PathVariable("id") BookId id,
                                     @Parameter(description = "Данные о книге") @Valid @RequestBody BookPatchRequest UserBook);

  @Operation(summary = "Удаление книги")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Книга успешно удалена",
          content = {@Content}),
      @ApiResponse(responseCode = "400", description = "Неверные данные запроса",
          content = {@Content})
  })
  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteBook(@Parameter(description = "ID книги") @Valid @PathVariable("id") BookId id);
}
