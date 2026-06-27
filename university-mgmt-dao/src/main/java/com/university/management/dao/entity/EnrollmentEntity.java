package com.university.management.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(
    name = "enrollments",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "uk_enrollments_student_course_semester",
          columnNames = {"student_id", "course_id", "semester"})
    })
public class EnrollmentEntity {

  @Id
  @Column(nullable = false, updatable = false)
  private UUID id = UUID.randomUUID();

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "student_id", nullable = false)
  private StudentEntity student;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "course_id", nullable = false)
  private CourseEntity course;

  @Column(nullable = false, length = 30)
  private String semester;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private EnrollmentStatusEntity status = EnrollmentStatusEntity.ENROLLED;

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
