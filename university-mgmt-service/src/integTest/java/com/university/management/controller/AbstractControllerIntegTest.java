package com.university.management.controller;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import com.university.management.UniversityManagementApplication;
import com.university.management.common.model.CourseCreateRequest;
import com.university.management.common.model.CourseResponse;
import com.university.management.common.model.StudentCreateRequest;
import com.university.management.common.model.StudentResponse;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RestClient;

@ActiveProfiles("integtest")
@SpringBootTest(
    classes = UniversityManagementApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractControllerIntegTest extends AbstractTestNGSpringContextTests {

  @LocalServerPort private int port;

  protected RestClient restClient() {
    return RestClient.builder().baseUrl("http://localhost:" + port).build();
  }

  protected String uniqueSuffix() {
    return UUID.randomUUID().toString().substring(0, 8);
  }

  protected StudentResponse createStudent(String suffix) {
    StudentCreateRequest request =
        new StudentCreateRequest()
            .firstName("Taylor")
            .lastName("Morgan")
            .email("student-" + suffix + "@example.edu")
            .dateOfBirth(LocalDate.of(2001, 4, 12));

    ResponseEntity<StudentResponse> response =
        restClient()
            .post()
            .uri("/api/v1/students")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .toEntity(StudentResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    assertNotNull(response.getBody());
    assertNotNull(response.getBody().getId());
    return response.getBody();
  }

  protected CourseResponse createCourse(String suffix) {
    CourseCreateRequest request =
        new CourseCreateRequest()
            .code("CS-" + suffix)
            .title("Introduction to Computer Science " + suffix)
            .credits(4)
            .department("Computer Science");

    ResponseEntity<CourseResponse> response =
        restClient()
            .post()
            .uri("/api/v1/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .toEntity(CourseResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    assertNotNull(response.getBody());
    assertNotNull(response.getBody().getId());
    return response.getBody();
  }
}
