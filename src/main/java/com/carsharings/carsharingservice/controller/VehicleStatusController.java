package com.carsharings.carsharingservice.controller;

import com.carsharings.carsharingservice.dto.VehicleEmergencyUpdate;
import com.carsharings.carsharingservice.dto.VehicleStatusUpdate;
import com.carsharings.carsharingservice.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
public class VehicleStatusController {

    private final VehicleService vehicleService;

    public VehicleStatusController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/{vehicle-id}/status")
    public ResponseEntity<?> updateVehicleStatus(@PathVariable("vehicle-id") String vehicleId,
                                                 @RequestBody VehicleStatusUpdate statusUpdate) {
        vehicleService.sendStatusUpdate(statusUpdate);
        return ResponseEntity.ok("Status update sent for vehicleId: " + vehicleId);
    }

    @PostMapping("/{vehicle-id}/alarm")
    public ResponseEntity<?> reportVehicleAlarm(@PathVariable("vehicle-id") String vehicleId,
                                                @RequestBody VehicleEmergencyUpdate emergencyUpdate) {
        vehicleService.sendEmergencyUpdate(emergencyUpdate);
        return ResponseEntity.ok("Emergency update sent for vehicleId: " + vehicleId);
    }
}
