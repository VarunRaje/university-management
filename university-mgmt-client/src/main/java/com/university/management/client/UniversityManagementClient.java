package com.university.management.client;

import com.university.management.common.model.CourseCreateRequest;
import com.university.management.common.model.CourseResponse;
import com.university.management.common.model.EnrollmentRequest;
import com.university.management.common.model.EnrollmentResponse;
import com.university.management.common.model.StudentCreateRequest;
import com.university.management.common.model.StudentResponse;
import com.university.management.common.model.StudentStatus;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

public class UniversityManagementClient {

  private static final ParameterizedTypeReference<List<StudentResponse>> STUDENTS_TYPE =
      new ParameterizedTypeReference<>() {};
  private static final ParameterizedTypeReference<List<CourseResponse>> COURSES_TYPE =
      new ParameterizedTypeReference<>() {};

  private final RestClient restClient;

  public UniversityManagementClient(RestClient.Builder restClientBuilder, URI baseUrl) {
    this.restClient = restClientBuilder.baseUrl(baseUrl.toString()).build();
  }

  public StudentResponse createStudent(StudentCreateRequest request) {
    return restClient
        .post()
        .uri("/api/v1/students")
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .body(StudentResponse.class);
  }

  public StudentResponse getStudent(UUID studentId) {
    return restClient
        .get()
        .uri("/api/v1/students/{studentId}", studentId)
        .retrieve()
        .body(StudentResponse.class);
  }

  public List<StudentResponse> listStudents(StudentStatus status) {
    return restClient
        .get()
        .uri(
            uriBuilder -> {
              uriBuilder.path("/api/v1/students");
              if (status != null) {
                uriBuilder.queryParam("status", status.getValue());
              }
              return uriBuilder.build();
            })
        .retrieve()
        .body(STUDENTS_TYPE);
  }

  public CourseResponse createCourse(CourseCreateRequest request) {
    return restClient
        .post()
        .uri("/api/v1/courses")
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .body(CourseResponse.class);
  }

  public CourseResponse getCourse(UUID courseId) {
    return restClient
        .get()
        .uri("/api/v1/courses/{courseId}", courseId)
        .retrieve()
        .body(CourseResponse.class);
  }

  public List<CourseResponse> listCourses() {
    return restClient.get().uri("/api/v1/courses").retrieve().body(COURSES_TYPE);
  }

  public EnrollmentResponse enrollStudent(EnrollmentRequest request) {
    return restClient
        .post()
        .uri("/api/v1/enrollments")
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .body(EnrollmentResponse.class);
  }
}
