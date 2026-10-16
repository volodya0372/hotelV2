package com.example.hostel_service.app.input.controller;

import com.example.hostel_service.app.dto.HotelCreateRequest;
import com.example.hostel_service.app.dto.HotelFull;
import com.example.hostel_service.app.dto.HotelSummary;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/property-view")
public interface HotelController {

    @Operation(
            summary = "Получить все отели",
            description = "Метод для получения списка всех отелей с их краткой информацией\n"
    )
    @GetMapping("/hotels")
    List<HotelSummary> all();

    @Operation(
            summary = "Получить отель по id",
            description = "Метод для получения расширенной информации по конкректному отелю"
    )
    @GetMapping("/hotels/{id}")
    HotelFull details(
            @PathVariable("id") Long id);

    @Operation(
            summary = "Получить отель по заданным параметрам",
            description = "Метод для поиска получения списка всех отелей с их краткой информацией по следующим параметрам: name, brand, city, country, amenities"
    )
    @GetMapping("/search")
    List<HotelSummary> search(@RequestParam(name = "name", required = false) String name,
                              @RequestParam(name = "brand", required = false) String brand,
                              @RequestParam(name = "city", required = false) String city,
                              @RequestParam(name = "country", required = false) String country,
                              @RequestParam(name = "amenities", required = false) String amenities);

    @Operation(
            summary = "Создать новый отель",
            description = "Метод для создания нового отеля"
    )
    @PostMapping("/hotels")
    HotelSummary create(@Valid @RequestBody HotelCreateRequest request);

    @Operation(
            summary = "Добавить дополнения к отелю",
            description = "Метод для добавления удобств в отель")
    @PostMapping("/hotels/{id}/amenities")
    HotelSummary addAmenities(
            @PathVariable("id") Long id,
            @RequestBody List<String> amenities);

    @Operation(
            summary = "Получение отелей по группам",
            description = "Метод для получение колличества отелей сгруппированных по каждому значению указанного параметра. Параметр: brand, city, country, amenities")
    @GetMapping("/histogram/{param}")
    Map<String, Long> histogram(
            @PathVariable("param") String param);
}
