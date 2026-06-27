package com.university.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableCaching
@EntityScan(basePackages = "com.university.management.dao.entity")
@EnableJpaRepositories(basePackages = "com.university.management.dao.repository")
@SpringBootApplication(scanBasePackages = "com.university.management")
public class UniversityManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(UniversityManagementApplication.class, args);
  }
}
