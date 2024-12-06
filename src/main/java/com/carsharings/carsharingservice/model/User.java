package com.carsharings.carsharingservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @NotBlank
    @Column(name = "username", unique = true, nullable = false)
    private String userName;

    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(nullable = false)
    private String userPassword;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String surName;

    @Min(18)
    @Column(nullable = false)
    private int age;

    @NotBlank
    @Column(name = "driving_license_number", nullable = false)
    private String drivingLicenseNumber;

    @NotBlank
    @Column(name = "credit_card_number", nullable = false)
    private String creditCardNumber;

    @NotBlank
    @Column(nullable = false)
    private String role;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
