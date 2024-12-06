package com.carsharings.carsharingservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @NotBlank(message = "Brand cannot be blank")
    @Size(max = 50, message = "Brand must not exceed 50 characters")
    @Column(nullable = false)
    private String brand;

    @NotBlank(message = "Model cannot be blank")
    @Size(max = 50, message = "Model must not exceed 50 characters")
    @Column(nullable = false)
    private String model;

    @Min(value = 1886, message = "Year must be 1886 or later")
    @Column(nullable = false)
    private int year;

    @NotBlank(message = "Color cannot be blank")
    @Size(max = 20, message = "Color must not exceed 20 characters")
    @Column(nullable = false)
    private String color;

    @NotBlank(message = "Registration number cannot be blank")
    @Size(max = 20, message = "Registration number must not exceed 20 characters")
    @Column(name = "registration_number", unique = true, nullable = false)
    private String registrationNumber;

    @NotBlank(message = "Status cannot be blank")
    @Column(nullable = false)
    private String status;

    @Embedded
    private EmergencyInfo emergencyInfo;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

