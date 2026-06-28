package com.university.management.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.university.management.common.api.StudentsApi;
import com.university.management.common.model.StudentCreateRequest;
import com.university.management.common.model.StudentResponse;
import com.university.management.common.model.StudentStatus;
import com.university.management.common.model.StudentUpdateRequest;
import com.university.management.service.StudentManagementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StudentController implements StudentsApi {

  private final StudentManagementService studentManagementService;

  @Override
  public ResponseEntity<StudentResponse> createStudent(StudentCreateRequest studentCreateRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(studentManagementService.createStudent(studentCreateRequest));
  }

  @Override
  public ResponseEntity<StudentResponse> getStudent(UUID studentId) {
    return ResponseEntity.ok(studentManagementService.getStudent(studentId));
  }

  @Override
  public ResponseEntity<List<StudentResponse>> listStudents(StudentStatus status) {
    return ResponseEntity.ok(studentManagementService.listStudents(status));
  }

  @Override
  public ResponseEntity<StudentResponse> updateStudent(
      UUID studentId, StudentUpdateRequest studentUpdateRequest) {
    return ResponseEntity.ok(
        studentManagementService.updateStudent(studentId, studentUpdateRequest));
  }
}
