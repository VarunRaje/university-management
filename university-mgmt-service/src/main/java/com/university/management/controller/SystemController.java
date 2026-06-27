package com.university.management.controller;

import com.university.management.common.api.SystemApi;
import com.university.management.common.model.HealthResponse;
import com.university.management.common.model.ServiceInfoResponse;
import com.university.management.service.service.SystemManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SystemController implements SystemApi {

  private final SystemManagementService systemManagementService;

  public SystemController(SystemManagementService systemManagementService) {
    this.systemManagementService = systemManagementService;
  }

  @Override
  public ResponseEntity<HealthResponse> getHealth() {
    return ResponseEntity.ok(systemManagementService.getHealth());
  }

  @Override
  public ResponseEntity<ServiceInfoResponse> getServiceInfo() {
    return ResponseEntity.ok(systemManagementService.getServiceInfo());
  }
}
