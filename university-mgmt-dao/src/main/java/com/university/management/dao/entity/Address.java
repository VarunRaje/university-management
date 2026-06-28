package com.university.management.dao.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {

  private String buildingNumber;
  private String street;
  private String city;
  private String state;
  private String pinCode;
  private String country;
}
