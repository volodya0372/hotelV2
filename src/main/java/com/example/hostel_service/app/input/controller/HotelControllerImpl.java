package com.example.hostel_service.app.input.controller;


import com.example.hostel_service.app.mapper.HotelMapper;
import com.example.hostel_service.core.service.HotelService;
import com.example.hostel_service.app.dto.HotelCreateRequest;
import com.example.hostel_service.app.dto.HotelFull;
import com.example.hostel_service.app.dto.HotelSummary;
import com.example.hostel_service.core.model.Hotel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HotelControllerImpl implements HotelController {

    HotelService hotelService;
    HotelMapper hotelMapper;

    public List<HotelSummary> all() {
        List<Hotel> allHotel = hotelService.findAll();
        return hotelMapper.toListHotelSummary(allHotel);
    }

    public HotelFull details(Long id) {
        Hotel hotel = hotelService.getById(id);
        return hotelMapper.toHotelFull(hotel);
    }

    public List<HotelSummary> search(String name, String brand, String city, String country, String amenities) {
        List<Hotel> result = hotelService.search(name, brand, city, country, amenities);
        return hotelMapper.toListHotelSummary(result);
    }

    public HotelSummary create(HotelCreateRequest request) {
        Hotel hotel = hotelService.create(hotelMapper.toHotel(request));
        return hotelMapper.toHotelSummary(hotel);
    }

    public HotelSummary addAmenities(Long id, List<String> amenities) {
        Hotel hotel = hotelService.addAmenities(id, amenities);
        return hotelMapper.toHotelSummary(hotel);
    }

    public Map<String, Long> histogram(String param) {
        return hotelService.histogram(param.toLowerCase(Locale.ROOT));
    }
}
