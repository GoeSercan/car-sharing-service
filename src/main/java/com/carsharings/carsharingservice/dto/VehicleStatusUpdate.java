package com.carsharings.carsharingservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleStatusUpdate implements Serializable {

    @NotBlank(message = "Vehicle ID must not be blank")
    private String vehicleId;

    @NotBlank(message = "Status must not be blank")
    private String status;
}

