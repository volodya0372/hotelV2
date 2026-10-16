package com.example.hostel_service.core.exception;

public class HotelNotFoundException extends RuntimeException {
    public HotelNotFoundException(Long id) {
        super("Отель не найден: " + id);
    }
}
