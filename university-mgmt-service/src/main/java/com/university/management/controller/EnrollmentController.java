package com.university.management.controller;

import com.university.management.common.api.EnrollmentsApi;
import com.university.management.common.model.EnrollmentRequest;
import com.university.management.common.model.EnrollmentResponse;
import com.university.management.service.service.EnrollmentManagementService;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class EnrollmentController implements EnrollmentsApi {

  private final EnrollmentManagementService enrollmentManagementService;

  public EnrollmentController(EnrollmentManagementService enrollmentManagementService) {
    this.enrollmentManagementService = enrollmentManagementService;
  }

  @Override
  public ResponseEntity<EnrollmentResponse> enrollStudent(EnrollmentRequest enrollmentRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(enrollmentManagementService.enrollStudent(enrollmentRequest));
  }

  @Override
  public ResponseEntity<EnrollmentResponse> getEnrollment(UUID enrollmentId) {
    return ResponseEntity.ok(enrollmentManagementService.getEnrollment(enrollmentId));
  }
}
