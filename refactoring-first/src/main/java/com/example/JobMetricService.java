package com.example;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JobMetricService {
    public List<JobMetric> getMetrics() {
        List<JobMetric> result = new ArrayList<>();
        for (Type type : Type.values()) {
            List<SubType> subTypes = getSubTypes(type);
            List<Area> areas = getAreas(type);
            if (subTypes.isEmpty()) {
              subTypes.add(SubType.NULL);
//                if (areas.isEmpty()) {
//                    JobMetric jobMetric = getMetric(type, Optional.empty(), Optional.empty());
//                    result.add(jobMetric);
//                } else {
//                    for (Area area : areas) {
//                        JobMetric jobMetric = getMetric(type, Optional.empty(), Optional.of(area));
//                        result.add(jobMetric);
//                    }
//                }
            }

            subTypes.stream().forEach(subType -> {
              if (subType == SubType.NULL){
                result.addAll(getJobMetrics(type, Optional.empty()).apply(areas));
              } else {
                result.addAll(getJobMetrics(type, Optional.of(subType)).apply(areas));
              }
            });


//            else {
//                for (SubType subType : subTypes) {
//                    if (areas.isEmpty()) {
//                        JobMetric jobMetric = getMetric(type, Optional.of(subType), Optional.empty());
//                        result.add(jobMetric);
//                    } else {
//                        for (Area area : areas) {
//                            JobMetric jobMetric = getMetric(type, Optional.of(subType), Optional.of(area));
//                            result.add(jobMetric);
//                        }
//                    }
//                }
//            }
        }
        return result;
    }


    Function<List<Area>, List<JobMetric>> getJobMetrics(Type type, Optional subType) {
        return areas -> {
            List<JobMetric> result = areas.stream().map(area -> getMetric(type, subType, Optional.of(area))).collect(Collectors.toList());
            if (result.isEmpty()) {
                result.add(getMetric(type, subType, Optional.empty()));
            }
            return result;
        };
    }

    private List<Area> getAreas(Type type) {
        if (type.equals(Type.A)) {
            return List.of();
        } else {
            return List.of(Area.GZ, Area.SZ);
        }
    }

    private List<SubType> getSubTypes(Type type) {
        if (type.equals(Type.A)) {
            return List.of();
        } else {
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
