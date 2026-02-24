package com.tracking.ubookit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing a named location with coordinates.
 * Used for store (pickup) and destination (delivery) locations.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    private String label;
    private double lat;
    private double lng;
}
