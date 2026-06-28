package com.university.management.controller;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;

import com.university.management.common.model.CourseResponse;

public class CourseControllerIntegTest extends AbstractControllerIntegTest {

  @Test
  public void createAndGetCourse() {
    CourseResponse createdCourse = createCourse(uniqueSuffix());

    ResponseEntity<CourseResponse> getResponse =
        restClient()
            .get()
            .uri("/api/v1/courses/{courseId}", createdCourse.getId())
            .retrieve()
            .toEntity(CourseResponse.class);

    assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
    assertNotNull(getResponse.getBody());
    assertEquals(getResponse.getBody().getId(), createdCourse.getId());
    assertEquals(getResponse.getBody().getCode(), createdCourse.getCode());
  }

  @Test
  public void listCoursesReturnsCreatedCourse() {
    CourseResponse createdCourse = createCourse(uniqueSuffix());

    ResponseEntity<CourseResponse[]> listResponse =
        restClient().get().uri("/api/v1/courses").retrieve().toEntity(CourseResponse[].class);

    assertEquals(listResponse.getStatusCode(), HttpStatus.OK);
    assertNotNull(listResponse.getBody());
    assertTrue(
        Arrays.stream(listResponse.getBody())
            .anyMatch(course -> createdCourse.getId().equals(course.getId())));
  }
}
