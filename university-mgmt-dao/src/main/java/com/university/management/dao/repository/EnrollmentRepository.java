package com.university.management.dao.repository;

import com.university.management.dao.entity.EnrollmentEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, UUID> {

  boolean existsByStudentIdAndCourseIdAndSemesterIgnoreCase(
      UUID studentId, UUID courseId, String semester);

  Optional<EnrollmentEntity> findByStudentIdAndCourseIdAndSemesterIgnoreCase(
      UUID studentId, UUID courseId, String semester);
}
