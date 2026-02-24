package com.tracking.ubookit.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TrackingResponse extends CommonResponse {

    private String id;
    private String status;
    private Location from;
    private Location to;
    private Coordinate driver;
    private int etaMinutes;
    private int progress;
    private List<Coordinate> route;
}
