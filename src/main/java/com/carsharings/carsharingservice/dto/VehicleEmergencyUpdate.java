package com.carsharings.carsharingservice.dto;

import com.carsharings.carsharingservice.model.EmergencyInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEmergencyUpdate implements Serializable {

    @NotBlank
    private String vehicleId;

    @NotNull
    private EmergencyInfo emergencyInfo;
}

