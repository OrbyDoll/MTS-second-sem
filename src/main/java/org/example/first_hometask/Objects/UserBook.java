package org.example.first_hometask.Objects;

public class UserBook {
  private Long id;
  private String title;
  private User bookAuthor;

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public Long getUserId() {
    return bookAuthor.getId();
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
