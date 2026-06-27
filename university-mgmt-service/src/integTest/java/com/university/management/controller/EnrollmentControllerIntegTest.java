package com.university.management.controller;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import com.university.management.common.model.CourseResponse;
import com.university.management.common.model.EnrollmentRequest;
import com.university.management.common.model.EnrollmentResponse;
import com.university.management.common.model.StudentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;

public class EnrollmentControllerIntegTest extends AbstractControllerIntegTest {

  @Test
  public void enrollStudentAndGetEnrollment() {
    String suffix = uniqueSuffix();
    StudentResponse student = createStudent(suffix);
    CourseResponse course = createCourse(suffix);
    EnrollmentRequest request =
        new EnrollmentRequest()
            .studentId(student.getId())
            .courseId(course.getId())
            .semester("Fall " + suffix);

    ResponseEntity<EnrollmentResponse> createResponse =
        restClient()
            .post()
            .uri("/api/v1/enrollments")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .toEntity(EnrollmentResponse.class);

    assertEquals(createResponse.getStatusCode(), HttpStatus.CREATED);
    assertNotNull(createResponse.getBody());
    assertEquals(createResponse.getBody().getStudentId(), student.getId());
    assertEquals(createResponse.getBody().getCourseId(), course.getId());

    ResponseEntity<EnrollmentResponse> getResponse =
        restClient()
            .get()
            .uri("/api/v1/enrollments/{enrollmentId}", createResponse.getBody().getId())
            .retrieve()
            .toEntity(EnrollmentResponse.class);

    assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
    assertNotNull(getResponse.getBody());
    assertEquals(getResponse.getBody().getId(), createResponse.getBody().getId());
  }
}
