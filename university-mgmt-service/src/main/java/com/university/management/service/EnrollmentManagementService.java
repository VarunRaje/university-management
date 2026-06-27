package com.university.management.service;

import com.university.management.common.model.EnrollmentRequest;
import com.university.management.common.model.EnrollmentResponse;
import com.university.management.dao.entity.CourseEntity;
import com.university.management.dao.entity.EnrollmentEntity;
import com.university.management.dao.entity.EnrollmentStatusEntity;
import com.university.management.dao.entity.StudentEntity;
import com.university.management.dao.repository.CourseRepository;
import com.university.management.dao.repository.EnrollmentRepository;
import com.university.management.dao.repository.StudentRepository;
import com.university.management.exception.ResourceConflictException;
import com.university.management.exception.ResourceNotFoundException;
import com.university.management.transformer.UniversityTransformer;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnrollmentManagementService {

  private final EnrollmentRepository enrollmentRepository;
  private final StudentRepository studentRepository;
  private final CourseRepository courseRepository;
  private final UniversityTransformer mapper;

  @Transactional
  public EnrollmentResponse enrollStudent(EnrollmentRequest request) {
    StudentEntity student =
        studentRepository
            .findById(request.getStudentId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("Student not found: " + request.getStudentId()));
    CourseEntity course =
        courseRepository
            .findById(request.getCourseId())
            .orElseThrow(
                () -> new ResourceNotFoundException("Course not found: " + request.getCourseId()));

    if (enrollmentRepository.existsByStudentIdAndCourseIdAndSemesterIgnoreCase(
        student.getId(), course.getId(), request.getSemester())) {
      throw new ResourceConflictException(
          "Student is already enrolled in this course for " + request.getSemester());
    }

    EnrollmentEntity entity = new EnrollmentEntity();
    entity.setStudent(student);
    entity.setCourse(course);
    entity.setSemester(request.getSemester().trim());
    entity.setStatus(EnrollmentStatusEntity.ENROLLED);

    return mapper.toEnrollmentResponse(enrollmentRepository.save(entity));
  }

  @Transactional(readOnly = true)
  public EnrollmentResponse getEnrollment(UUID enrollmentId) {
    return mapper.toEnrollmentResponse(
        enrollmentRepository
            .findById(enrollmentId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Enrollment not found: " + enrollmentId)));
  }
}
