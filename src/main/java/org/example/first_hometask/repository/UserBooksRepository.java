package org.example.first_hometask.repository;

import org.example.first_hometask.model.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBooksRepository extends JpaRepository<UserBook, Long> {
  Optional<UserBook> findById(Long bookId);
}
