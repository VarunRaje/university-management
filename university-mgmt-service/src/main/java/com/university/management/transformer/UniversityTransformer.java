package com.university.management.transformer;

import com.university.management.common.model.CourseResponse;
import com.university.management.common.model.EnrollmentResponse;
import com.university.management.common.model.EnrollmentStatus;
import com.university.management.common.model.StudentResponse;
import com.university.management.common.model.StudentStatus;
import com.university.management.dao.entity.CourseEntity;
import com.university.management.dao.entity.EnrollmentEntity;
import com.university.management.dao.entity.EnrollmentStatusEntity;
import com.university.management.dao.entity.StudentEntity;
import com.university.management.dao.entity.StudentStatusEntity;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Component;

@Component
public class UniversityTransformer {

  public StudentResponse toStudentResponse(StudentEntity entity) {
    return new StudentResponse()
        .id(entity.getId())
        .studentNumber(entity.getStudentNumber())
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .email(entity.getEmail())
        .dateOfBirth(entity.getDateOfBirth())
        .status(StudentStatus.fromValue(entity.getStatus().name()))
        .createdAt(toOffsetDateTime(entity.getCreatedAt()))
        .updatedAt(toOffsetDateTime(entity.getUpdatedAt()));
  }

  public CourseResponse toCourseResponse(CourseEntity entity) {
    return new CourseResponse()
        .id(entity.getId())
        .code(entity.getCode())
        .title(entity.getTitle())
        .credits(entity.getCredits())
        .department(entity.getDepartment())
        .createdAt(toOffsetDateTime(entity.getCreatedAt()))
        .updatedAt(toOffsetDateTime(entity.getUpdatedAt()));
  }

  public EnrollmentResponse toEnrollmentResponse(EnrollmentEntity entity) {
    return new EnrollmentResponse()
        .id(entity.getId())
        .studentId(entity.getStudent().getId())
        .studentNumber(entity.getStudent().getStudentNumber())
        .courseId(entity.getCourse().getId())
        .courseCode(entity.getCourse().getCode())
        .semester(entity.getSemester())
        .status(EnrollmentStatus.fromValue(entity.getStatus().name()))
        .createdAt(toOffsetDateTime(entity.getCreatedAt()))
        .updatedAt(toOffsetDateTime(entity.getUpdatedAt()));
  }

  public StudentStatusEntity toStudentStatusEntity(StudentStatus status) {
    return StudentStatusEntity.valueOf(status.getValue());
  }

  public EnrollmentStatusEntity toEnrollmentStatusEntity(EnrollmentStatus status) {
    return EnrollmentStatusEntity.valueOf(status.getValue());
  }

  private OffsetDateTime toOffsetDateTime(Instant instant) {
    if (instant == null) {
      return null;
    }
    return OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
  }
}
