package com.example.hostel_service.app.dto;

public record HotelSummary(
        Long id,
        String name,
        String description,
        String address,
        String phone
) {}
