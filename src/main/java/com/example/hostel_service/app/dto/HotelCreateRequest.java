package com.example.hostel_service.app.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalTime;

public record HotelCreateRequest(
        @NotBlank
        String name,
        String description,
        String brand,
        Integer houseNumber,
        String street,
        String city,
        String country,
        String postCode,
        String phone,

        String email,
        LocalTime checkIn,
        LocalTime checkOut) {
}
