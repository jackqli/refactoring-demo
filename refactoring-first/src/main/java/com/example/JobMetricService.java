package com.example;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobMetricService {
  public List<JobMetric> getMetrics() {
    List<JobMetric> result = new ArrayList<>();
    for (Type type : Type.values()) {
      List<SubType> subTypes = getSubTypes(type);
      List<Area> areas = getAreas(type);

      if (subTypes.isEmpty()) {
        if (areas.isEmpty()) {
          JobMetric jobMetric = getMetric(type, Optional.empty(), Optional.empty());
          result.add(jobMetric);
        }
        else {
          for (Area area : areas) {
            JobMetric jobMetric = getMetric(type, Optional.empty(), Optional.of(area));
            result.add(jobMetric);
          }
        }
      }
      else {
        for (SubType subType : subTypes) {
          if (areas.isEmpty()) {
            JobMetric jobMetric = getMetric(type, Optional.of(subType), Optional.empty());
            result.add(jobMetric);
          }
          else {
            for (Area area : areas) {
              JobMetric jobMetric = getMetric(type, Optional.of(subType), Optional.of(area));
              result.add(jobMetric);
            }
          }
        }
      }
    }


    return result;
  }

  private List<Area> getAreas(Type type) {
    if (type.equals(Type.A)) {
      return List.of();
    }
    else {
      return List.of(Area.GZ, Area.SZ);
    }
  }

  private List<SubType> getSubTypes(Type type) {
    if (type.equals(Type.A)) {
      return List.of();
    }
    else {
      return List.of(SubType.AA, SubType.BB);
    }
  }

  public JobMetric getMetric(Type type,
                            Optional<SubType> subType,
                            Optional<Area> area) {
    return JobMetric.builder()
        .type(type.toString())
        .subType(subType.isPresent() ? subType.get().toString() : "ALL")
        .area(area.isPresent() ? area.get().toString() : "ALL")
        .build();
  }
}
