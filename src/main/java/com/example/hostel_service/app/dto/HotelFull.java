package com.example.hostel_service.app.dto;

import java.time.LocalTime;
import java.util.Set;

public record HotelFull(
        Long id,
        String name,
        String description,
        String brand,
        Address address,
        Contacts contacts,
        ArrivalTime arrivalTime,
        Set<String> amenities
) {
    public record Address(
            Integer houseNumber,
            String street,
            String city,
            String country,
            String postCode
    ) {
    }

    public record Contacts(
            String phone,
            String email
    ) {
    }

    public record ArrivalTime(
            LocalTime checkIn,
            LocalTime checkOut
    ) {
    }
}
