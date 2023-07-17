package com.expatrio.usermanagement.model.payload.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

/**
 * The type Base response.
 */
@ToString
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"status", "message", "description", "timestamp"})
public class BaseResponse {

    private int status;
    private String message;
    private String description;
    private Instant timestamp;

}