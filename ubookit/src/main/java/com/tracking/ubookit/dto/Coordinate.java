package com.tracking.ubookit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing a geographic coordinate (latitude/longitude pair).
 * Used for driver position and route waypoints.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinate {

    private double lat;
    private double lng;
}
