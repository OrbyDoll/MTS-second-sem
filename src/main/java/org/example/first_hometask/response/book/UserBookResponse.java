package org.example.first_hometask.response.book;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserBookResponse {
    private Long id;
    private String title;
    private Long author_id;
}
