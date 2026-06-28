package com.university.management.controller;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;

import com.university.management.common.model.StudentResponse;
import com.university.management.common.model.StudentStatus;
import com.university.management.common.model.StudentUpdateRequest;

public class StudentControllerIntegTest extends AbstractControllerIntegTest {

  @Test
  public void createAndGetStudent() {
    StudentResponse createdStudent = createStudent(uniqueSuffix());

    ResponseEntity<StudentResponse> getResponse =
        restClient()
            .get()
            .uri("/api/v1/students/{studentId}", createdStudent.getId())
            .retrieve()
            .toEntity(StudentResponse.class);

    assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
    assertNotNull(getResponse.getBody());
    assertEquals(getResponse.getBody().getId(), createdStudent.getId());
    assertEquals(getResponse.getBody().getEmail(), createdStudent.getEmail());
  }

  @Test
  public void listStudentsByStatusReturnsCreatedStudent() {
    StudentResponse createdStudent = createStudent(uniqueSuffix());

    ResponseEntity<StudentResponse[]> listResponse =
        restClient()
            .get()
            .uri("/api/v1/students?status=ACTIVE")
            .retrieve()
            .toEntity(StudentResponse[].class);

    assertEquals(listResponse.getStatusCode(), HttpStatus.OK);
    assertNotNull(listResponse.getBody());
    assertTrue(
        Arrays.stream(listResponse.getBody())
            .anyMatch(student -> createdStudent.getId().equals(student.getId())));
  }

  @Test
  public void updateStudent() {
    String suffix = uniqueSuffix();
    StudentResponse createdStudent = createStudent(suffix);
    StudentUpdateRequest request =
        new StudentUpdateRequest()
            .firstName("Jordan")
            .lastName("Morgan")
            .email("updated-student-" + suffix + "@example.edu")
            .dateOfBirth(LocalDate.of(2002, 6, 14))
            .status(StudentStatus.INACTIVE);

    ResponseEntity<StudentResponse> updateResponse =
        restClient()
            .put()
            .uri("/api/v1/students/{studentId}", createdStudent.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .toEntity(StudentResponse.class);

    assertEquals(updateResponse.getStatusCode(), HttpStatus.OK);
    assertNotNull(updateResponse.getBody());
    assertEquals(updateResponse.getBody().getId(), createdStudent.getId());
    assertEquals(updateResponse.getBody().getFirstName(), "Jordan");
    assertEquals(updateResponse.getBody().getStatus(), StudentStatus.INACTIVE);
  }
}
