package com.university.management.service.service;

import com.university.management.common.model.StudentCreateRequest;
import com.university.management.common.model.StudentResponse;
import com.university.management.common.model.StudentStatus;
import com.university.management.common.model.StudentUpdateRequest;
import com.university.management.dao.entity.StudentEntity;
import com.university.management.dao.entity.StudentStatusEntity;
import com.university.management.dao.repository.StudentRepository;
import com.university.management.service.exception.ResourceConflictException;
import com.university.management.service.exception.ResourceNotFoundException;
import com.university.management.service.transformer.UniversityTransformer;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentManagementService {

  private final StudentRepository studentRepository;
  private final UniversityTransformer mapper;

  public StudentManagementService(
      StudentRepository studentRepository, UniversityTransformer mapper) {
    this.studentRepository = studentRepository;
    this.mapper = mapper;
  }

  @Transactional
  public StudentResponse createStudent(StudentCreateRequest request) {
    String email = normalizeEmail(request.getEmail());
    if (studentRepository.existsByEmailIgnoreCase(email)) {
      throw new ResourceConflictException("Student email already exists: " + request.getEmail());
    }

    StudentEntity entity = new StudentEntity();
    entity.setStudentNumber(generateStudentNumber());
    entity.setFirstName(request.getFirstName().trim());
    entity.setLastName(request.getLastName().trim());
    entity.setEmail(email);
    entity.setDateOfBirth(request.getDateOfBirth());
    entity.setStatus(StudentStatusEntity.ACTIVE);

    return mapper.toStudentResponse(studentRepository.save(entity));
  }

  @Transactional(readOnly = true)
  public List<StudentResponse> listStudents(StudentStatus status) {
    List<StudentEntity> students;
    if (status == null) {
      students = studentRepository.findAll();
    } else {
      students = studentRepository.findByStatus(mapper.toStudentStatusEntity(status));
    }
    return students.stream().map(mapper::toStudentResponse).toList();
  }

  @Cacheable(cacheNames = "students", key = "#studentId")
  @Transactional(readOnly = true)
  public StudentResponse getStudent(UUID studentId) {
    return mapper.toStudentResponse(findStudent(studentId));
  }

  @CacheEvict(cacheNames = "students", key = "#studentId")
  @Transactional
  public StudentResponse updateStudent(UUID studentId, StudentUpdateRequest request) {
    StudentEntity entity = findStudent(studentId);
    String email = normalizeEmail(request.getEmail());
    studentRepository
        .findByEmailIgnoreCase(email)
        .filter(existing -> !existing.getId().equals(studentId))
        .ifPresent(
            existing -> {
              throw new ResourceConflictException(
                  "Student email already exists: " + request.getEmail());
            });

    entity.setFirstName(request.getFirstName().trim());
    entity.setLastName(request.getLastName().trim());
    entity.setEmail(email);
    entity.setDateOfBirth(request.getDateOfBirth());
    entity.setStatus(mapper.toStudentStatusEntity(request.getStatus()));

    return mapper.toStudentResponse(studentRepository.save(entity));
  }

  private StudentEntity findStudent(UUID studentId) {
    return studentRepository
        .findById(studentId)
        .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));
  }

  private String normalizeEmail(String email) {
    return email.trim().toLowerCase(Locale.ROOT);
  }

  private String generateStudentNumber() {
    for (int attempt = 0; attempt < 5; attempt++) {
      String candidate =
          "STU-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT);
      if (!studentRepository.existsByStudentNumber(candidate)) {
        return candidate;
      }
    }
    throw new ResourceConflictException("Unable to allocate a unique student number");
  }
}
