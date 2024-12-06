package com.carsharings.carsharingservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable

public class EmergencyInfo implements Serializable {

    @NotBlank(message = "Status cannot be blank")
    @Size(max = 50, message = "Status must not exceed 50 characters")
    private String status;

    @Column(name = "occupied_by_driver")
    private boolean occupiedByDriver;

    @Min(value = 1, message = "Priority must be at least 1")
    @Max(value = 5, message = "Priority cannot exceed 5")
    @Column(nullable = false)
    private int priority;

    @NotBlank(message = "Emergency description cannot be blank")
    @Size(max = 255, message = "Emergency description must not exceed 255 characters")
    @Column(name = "emergency_description", nullable = false)
    private String emergencyDescription;
}
