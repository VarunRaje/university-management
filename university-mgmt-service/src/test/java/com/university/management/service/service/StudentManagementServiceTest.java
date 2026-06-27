package com.university.management.service.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import com.university.management.common.model.StudentCreateRequest;
import com.university.management.common.model.StudentResponse;
import com.university.management.dao.entity.StudentEntity;
import com.university.management.dao.repository.StudentRepository;
import com.university.management.service.StudentManagementService;
import com.university.management.transformer.UniversityTransformer;
import java.time.Instant;
import java.time.LocalDate;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StudentManagementServiceTest {

  @Mock private StudentRepository studentRepository;

  private AutoCloseable mocks;
  private StudentManagementService service;

  @BeforeMethod
  public void setUp() {
    mocks = MockitoAnnotations.openMocks(this);
    service = new StudentManagementService(studentRepository, new UniversityTransformer());
  }

  @AfterMethod
  public void tearDown() throws Exception {
    mocks.close();
  }

  @Test
  public void createStudentNormalizesEmailAndAssignsStudentNumber() {
    when(studentRepository.existsByEmailIgnoreCase("alex.student@example.edu")).thenReturn(false);
    when(studentRepository.existsByStudentNumber(anyString())).thenReturn(false);
    when(studentRepository.save(any(StudentEntity.class)))
        .thenAnswer(
            invocation -> {
              StudentEntity entity = invocation.getArgument(0);
              entity.setCreatedAt(Instant.parse("2026-01-01T00:00:00Z"));
              entity.setUpdatedAt(Instant.parse("2026-01-01T00:00:00Z"));
              return entity;
            });

    StudentCreateRequest request =
        new StudentCreateRequest()
            .firstName("Alex")
            .lastName("Student")
            .email("  Alex.Student@Example.edu ")
            .dateOfBirth(LocalDate.of(2002, 5, 10));

    StudentResponse response = service.createStudent(request);

    assertNotNull(response.getId());
    assertNotNull(response.getStudentNumber());
    assertEquals(response.getEmail(), "alex.student@example.edu");
    assertEquals(response.getFirstName(), "Alex");
    verify(studentRepository).save(any(StudentEntity.class));
  }
}
