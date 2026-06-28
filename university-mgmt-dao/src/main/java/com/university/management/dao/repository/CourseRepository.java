package com.university.management.dao.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.management.dao.entity.CourseEntity;

public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {

  boolean existsByCodeIgnoreCase(String code);

  Optional<CourseEntity> findByCodeIgnoreCase(String code);
}
