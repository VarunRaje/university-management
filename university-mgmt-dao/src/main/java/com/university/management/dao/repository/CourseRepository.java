package com.university.management.dao.repository;

import com.university.management.dao.entity.CourseEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {

  boolean existsByCodeIgnoreCase(String code);

  Optional<CourseEntity> findByCodeIgnoreCase(String code);
}
