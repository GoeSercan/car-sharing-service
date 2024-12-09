package com.carsharings.carsharingservice.service;

import com.carsharings.carsharingservice.dto.VehicleEmergencyUpdate;
import com.carsharings.carsharingservice.dto.VehicleStatusUpdate;
import com.carsharings.carsharingservice.model.Vehicle;
import com.carsharings.carsharingservice.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private static final Logger logger = LoggerFactory.getLogger(VehicleService.class);

    private final VehicleRepository vehicleRepository;
    private final RabbitTemplate rabbitTemplate;

    public VehicleService(VehicleRepository vehicleRepository, RabbitTemplate rabbitTemplate) {
        this.vehicleRepository = vehicleRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public Vehicle registerVehicle(Vehicle newVehicle) {
        if (newVehicle == null) {
            throw new IllegalArgumentException("New vehicle cannot be null");
        }
        if (vehicleRepository.existsById(newVehicle.getId())) {
            throw new IllegalArgumentException("Vehicle already exists with ID: " + newVehicle.getId());
        }
        Vehicle savedVehicle = vehicleRepository.save(newVehicle);
        logger.info("Registered new vehicle: {}", savedVehicle);
        return savedVehicle;
    }

    public List<Vehicle> getAllVehicles() {
        logger.info("Fetching all vehicles");
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getVehicleById(String id) {
        logger.info("Fetching vehicle with ID: {}", id);
        return vehicleRepository.findById(id);
    }

    @Transactional
    public Optional<Vehicle> updateVehicle(String id, Vehicle updatedVehicle) {
        return vehicleRepository.findById(id).map(existingVehicle -> {
            updatedVehicle.setId(existingVehicle.getId());
            Vehicle savedVehicle = vehicleRepository.save(updatedVehicle);
            logger.info("Updated vehicle: {}", savedVehicle);
            return savedVehicle;
        });
    }

    @Transactional
    public boolean deleteVehicle(String id) {
        if (!vehicleRepository.existsById(id)) {
            logger.warn("Attempted to delete non-existent vehicle with ID: {}", id);
            return false;
        }
        vehicleRepository.deleteById(id);
        logger.info("Deleted vehicle with ID: {}", id);
        return true;
    }

    public void sendStatusUpdate(VehicleStatusUpdate statusUpdate) {
        try {
            rabbitTemplate.convertAndSend("carSharingExchange", "UpdateStatusQueue", statusUpdate);
            logger.info("Status update sent to RabbitMQ: {}", statusUpdate);
        } catch (Exception e) {
            logger.error("Failed to send status update: {}", statusUpdate, e);
        }
    }

    public void sendEmergencyUpdate(VehicleEmergencyUpdate emergencyUpdate) {
        try {
            rabbitTemplate.convertAndSend("carSharingExchange", "EmergencyQueue", emergencyUpdate);
            logger.info("Emergency update sent to RabbitMQ: {}", emergencyUpdate);
        } catch (Exception e) {
            logger.error("Failed to send emergency update: {}", emergencyUpdate, e);
        }
    }
}
