package com.example;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobMetric {
  private String type;
  private String subType;
  private String area;
  private String status;
}
