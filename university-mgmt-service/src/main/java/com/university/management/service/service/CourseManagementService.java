package com.university.management.service.service;

import com.university.management.common.model.CourseCreateRequest;
import com.university.management.common.model.CourseResponse;
import com.university.management.dao.entity.CourseEntity;
import com.university.management.dao.repository.CourseRepository;
import com.university.management.service.exception.ResourceConflictException;
import com.university.management.service.exception.ResourceNotFoundException;
import com.university.management.service.transformer.UniversityTransformer;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseManagementService {

  private final CourseRepository courseRepository;
  private final UniversityTransformer mapper;

  public CourseManagementService(CourseRepository courseRepository, UniversityTransformer mapper) {
    this.courseRepository = courseRepository;
    this.mapper = mapper;
  }

  @Transactional
  public CourseResponse createCourse(CourseCreateRequest request) {
    String courseCode = request.getCode().trim().toUpperCase(Locale.ROOT);
    if (courseRepository.existsByCodeIgnoreCase(courseCode)) {
      throw new ResourceConflictException("Course code already exists: " + request.getCode());
    }

    CourseEntity entity = new CourseEntity();
    entity.setCode(courseCode);
    entity.setTitle(request.getTitle().trim());
    entity.setCredits(request.getCredits());
    entity.setDepartment(request.getDepartment().trim());

    return mapper.toCourseResponse(courseRepository.save(entity));
  }

  @Transactional(readOnly = true)
  public CourseResponse getCourse(UUID courseId) {
    return mapper.toCourseResponse(findCourse(courseId));
  }

  @Transactional(readOnly = true)
  public List<CourseResponse> listCourses() {
    return courseRepository.findAll().stream().map(mapper::toCourseResponse).toList();
  }

  CourseEntity findCourse(UUID courseId) {
    return courseRepository
        .findById(courseId)
        .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));
  }
}
