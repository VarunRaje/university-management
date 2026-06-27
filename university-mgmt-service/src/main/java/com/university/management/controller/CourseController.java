package com.university.management.controller;

import com.university.management.common.api.CoursesApi;
import com.university.management.common.model.CourseCreateRequest;
import com.university.management.common.model.CourseResponse;
import com.university.management.service.CourseManagementService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CourseController implements CoursesApi {

  private final CourseManagementService courseManagementService;

  @Override
  public ResponseEntity<CourseResponse> createCourse(CourseCreateRequest courseCreateRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(courseManagementService.createCourse(courseCreateRequest));
  }

  @Override
  public ResponseEntity<CourseResponse> getCourse(UUID courseId) {
    return ResponseEntity.ok(courseManagementService.getCourse(courseId));
  }

  @Override
  public ResponseEntity<List<CourseResponse>> listCourses() {
    return ResponseEntity.ok(courseManagementService.listCourses());
  }
}
