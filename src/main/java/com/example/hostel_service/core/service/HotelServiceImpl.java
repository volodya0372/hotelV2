package com.example.hostel_service.core.service;

import com.example.hostel_service.core.model.Hotel;
import com.example.hostel_service.core.storage.HotelStorage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Transactional
public class HotelServiceImpl implements HotelService {
    HotelStorage storage;

    @Transactional(readOnly = true)
    public List<Hotel> findAll() {
        return storage.findAllHostels();
    }

    @Transactional(readOnly = true)
    public Hotel getById(Long id) {
        return storage.findHostelById(id);
    }

    public Hotel create(Hotel hotel) {
        return storage.createHostel(hotel);
    }

    public Hotel addAmenities(Long id, List<String> amenities) {
        Hotel h = getById(id);
        h.getAmenities().addAll(amenities);
        return storage.createHostel(h);
    }

    @Transactional(readOnly = true)
    public List<Hotel> search(String name, String brand, String city, String country, String amenity) {
        return storage.findAllHostelsByParam(name, brand, city, country, amenity);
    }

    @Transactional(readOnly = true)
    public Map<String, Long> histogram(String param) {
        if (!Set.of("brand", "city", "country", "amenities").contains(param))
            throw new IllegalArgumentException("Неподдерживающий параметр: " + param);
        Map<String, Long> result = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (Hotel h : storage.findAllHostels()) {
            if (param.equals("amenities")) h.getAmenities().forEach(a -> result.merge(a, 1L, Long::sum));
            else {
                String value = switch (param) {
                    case "brand" -> h.getBrand();
                    case "city" -> h.getAddress() == null ? null : h.getAddress().getCity();
                    default -> h.getAddress() == null ? null : h.getAddress().getCountry();
                };
                if (value != null && !value.isBlank()) result.merge(value, 1L, Long::sum);
            }
        }
        return result;
    }
}
