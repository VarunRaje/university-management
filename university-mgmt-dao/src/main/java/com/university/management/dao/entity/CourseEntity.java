package com.university.management.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "courses")
public class CourseEntity {

  @Id
  @Column(nullable = false, updatable = false)
  private UUID id = UUID.randomUUID();

  @Column(nullable = false, unique = true, length = 30)
  private String code;

  @Column(nullable = false, length = 200)
  private String title;

  @Column(nullable = false)
  private Integer credits;

  @Column(nullable = false, length = 120)
  private String department;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  @PrePersist
  void prePersist() {
    Instant now = Instant.now();
    createdAt = now;
    updatedAt = now;
  }

  @PreUpdate
  void preUpdate() {
    updatedAt = Instant.now();
  }
}
