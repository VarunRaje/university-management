package com.university.management.service;

import com.university.management.common.model.HealthResponse;
import com.university.management.common.model.ServiceInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.stereotype.Service;

@Service
public class SystemManagementService {

  private final String serviceName;

  public SystemManagementService(@Value("${spring.application.name}") String serviceName) {
    this.serviceName = serviceName;
  }

  public HealthResponse getHealth() {
    return new HealthResponse().status("UP");
  }

  public ServiceInfoResponse getServiceInfo() {
    return new ServiceInfoResponse()
        .serviceName(serviceName)
        .springVersion(SpringBootVersion.getVersion());
  }
}
