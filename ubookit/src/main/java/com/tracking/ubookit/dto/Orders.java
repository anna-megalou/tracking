package com.tracking.ubookit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders { 

    private String id;
    private String status;
    private int items;
    private double price;
    private String store;
    private String city;
    private String postalCode;
}
