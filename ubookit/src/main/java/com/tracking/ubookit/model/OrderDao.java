package com.tracking.ubookit.model;

import com.tracking.ubookit.dto.Coordinate;
import com.tracking.ubookit.dto.Location;
import com.tracking.ubookit.dto.Orders;
import com.tracking.ubookit.dto.TrackingResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "orders_tracking")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDao {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Id
    private String id;

    private String status;

    private int items;

    private double price;

    private String store;

    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "store_lat")
    private double storeLat;

    @Column(name = "store_lng")
    private double storeLng;

    @Column(name = "destination_lat")
    private double destinationLat;

    @Column(name = "destination_lng")
    private double destinationLng;

    @Column(name = "destination_label")
    private String destinationLabel;

    @Column(name = "driver_lat")
    private double driverLat;

    @Column(name = "driver_lng")
    private double driverLng;

    @Column(name = "eta_minutes")
    private int etaMinutes;

    private int progress;

    @Column(name = "route", columnDefinition = "TEXT")
    private String route;

    public Orders toDto() {
        return Orders.builder()
                .id(this.id)
                .status(this.status)
                .items(this.items)
                .price(this.price)
                .store(this.store)
                .city(this.city)
                .postalCode(this.postalCode)
                .build();
    }

    public TrackingResponse toTrackingResponse() {
        return TrackingResponse.builder()
                .id(this.id)
                .status(this.status)
                .from(new Location(this.store, this.storeLat, this.storeLng))
                .to(new Location(this.destinationLabel, this.destinationLat, this.destinationLng))
                .driver(new Coordinate(this.driverLat, this.driverLng))
                .etaMinutes(this.etaMinutes)
                .progress(this.progress)
                .route(parseRoute())
                .build();
    }

    private List<Coordinate> parseRoute() {
        if (this.route == null || this.route.isBlank()) {
            return Collections.emptyList();
        }
        try {
            return mapper.readValue(this.route, new TypeReference<List<Coordinate>>() {});
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
