package org.example.first_hometask.repository;

import org.example.first_hometask.model.OutboxRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRecordsRepository extends JpaRepository<OutboxRecord, Long> {}
