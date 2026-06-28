package com.university.management.dao.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.management.dao.entity.StudentEntity;
import com.university.management.dao.entity.StudentStatusEntity;

public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {

  boolean existsByEmailIgnoreCase(String email);

  boolean existsByStudentNumber(String studentNumber);

  List<StudentEntity> findByStatus(StudentStatusEntity status);

  Optional<StudentEntity> findByEmailIgnoreCase(String email);
}
