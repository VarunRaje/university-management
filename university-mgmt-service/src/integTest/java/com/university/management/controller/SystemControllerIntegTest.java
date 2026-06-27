package com.university.management.controller;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import com.university.management.common.model.HealthResponse;
import com.university.management.common.model.ServiceInfoResponse;
import org.springframework.boot.SpringBootVersion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;

public class SystemControllerIntegTest extends AbstractControllerIntegTest {

  @Test
  public void getHealthReturnsUp() {
    ResponseEntity<HealthResponse> response =
        restClient().get().uri("/api/v1/health").retrieve().toEntity(HealthResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertNotNull(response.getBody());
    assertEquals(response.getBody().getStatus(), "UP");
  }

  @Test
  public void getServiceInfoReturnsServiceMetadata() {
    ResponseEntity<ServiceInfoResponse> response =
        restClient().get().uri("/api/v1/info").retrieve().toEntity(ServiceInfoResponse.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertNotNull(response.getBody());
    assertEquals(response.getBody().getServiceName(), "university-management-service");
    assertEquals(response.getBody().getSpringVersion(), SpringBootVersion.getVersion());
  }
}
