package com.example.hostel_service.app.mapper;

import com.example.hostel_service.app.output.entity.HotelEntity;
import com.example.hostel_service.core.model.Hotel;
import com.example.hostel_service.app.dto.HotelCreateRequest;
import com.example.hostel_service.app.dto.HotelFull;
import com.example.hostel_service.app.dto.HotelSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    @Mapping(target = "address", expression = "java(hotel.getAddress().formatted())")
    @Mapping(target = "phone", source = "hotel.contacts.phone")
    HotelSummary toHotelSummary(Hotel hotel);
    List<HotelSummary> toListHotelSummary(List<Hotel> hotels);

    List<Hotel> toListHotel(List<HotelEntity> listEntity);

    Hotel toHotel(HotelEntity hotel);

    HotelEntity toEntity(Hotel hotel);

    @Mapping(target = "address.houseNumber", source = "request.houseNumber")
    @Mapping(target = "address.street", source = "request.street")
    @Mapping(target = "address.city", source = "request.city")
    @Mapping(target = "address.country", source = "request.country")
    @Mapping(target = "address.postCode", source = "request.postCode")
    @Mapping(target = "contacts.phone", source = "request.phone")
    @Mapping(target = "contacts.email", source = "request.email")
    @Mapping(target = "arrivalTime.checkIn", source = "request.checkIn")
    @Mapping(target = "arrivalTime.checkOut", source = "request.checkOut")
    Hotel toHotel(HotelCreateRequest request);

    HotelFull toHotelFull(Hotel hotel);
}
