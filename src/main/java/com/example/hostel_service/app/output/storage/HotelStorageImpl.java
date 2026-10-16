package com.example.hostel_service.app.output.storage;

import com.example.hostel_service.app.mapper.HotelMapper;
import com.example.hostel_service.app.output.entity.HotelEntity;
import com.example.hostel_service.app.output.repository.HotelRepository;
import com.example.hostel_service.core.exception.HotelNotFoundException;
import com.example.hostel_service.core.model.Hotel;
import com.example.hostel_service.core.storage.HotelStorage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HotelStorageImpl implements HotelStorage{
    HotelRepository hotelRepository;
    HotelMapper hotelMapper;
    @Override
    public List<Hotel> findAllHostels() {
        List<HotelEntity> result = hotelRepository.findAll();
        return hotelMapper.toListHotel(result);
    }

    @Override
    public List<Hotel> findAllHostelsByParam(String name, String brand, String city, String country, String amenity) {
        Specification<HotelEntity> spec = (root, q, cb) -> cb.conjunction();
        if (has(name)) spec = spec.and((r, q, cb) -> cb.like(cb.lower(r.get("name")), "%" + name.toLowerCase() + "%"));
        if (has(brand))
            spec = spec.and((r, q, cb) -> cb.like(cb.lower(r.get("brand")), "%" + brand.toLowerCase() + "%"));
        if (has(city))
            spec = spec.and((r, q, cb) -> cb.like(cb.lower(r.get("address").get("city")), "%" + city.toLowerCase() + "%"));
        if (has(country))
            spec = spec.and((r, q, cb) -> cb.like(cb.lower(r.get("address").get("country")), "%" + country.toLowerCase() + "%"));
        if (has(amenity)) spec = spec.and((r, q, cb) -> cb.isMember(amenity, r.get("amenities")));
        List<HotelEntity> hotels = hotelRepository.findAll(spec);
        return hotelMapper.toListHotel(hotels);
    }

    @Override
    public Hotel findHostelById(Long id) {
        HotelEntity entity = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
        return hotelMapper.toHotel(entity);
    }

    @Override
    public Hotel createHostel(Hotel hotel) {
        HotelEntity newHotel = hotelMapper.toEntity(hotel);
        HotelEntity save = hotelRepository.save(newHotel);
        return hotelMapper.toHotel(save);

    }

    private boolean has(String s) {
        return s != null && !s.isBlank();
    }
}
