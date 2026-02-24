package com.tracking.ubookit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Base response DTO shared by all API responses.
 * Contains a status code, message, and optional description for error details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CommonResponse {
    private String message;
    private Integer code;
    private String description;
}
