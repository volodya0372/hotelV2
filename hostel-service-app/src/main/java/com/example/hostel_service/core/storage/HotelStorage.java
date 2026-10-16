package com.example.hostel_service.core.storage;

import com.example.hostel_service.core.model.Hotel;

import java.util.List;

/**
 * Слой между бизнес логикой и репозиторием
 */
public interface HotelStorage {
    /**
     * Поиск всех отелей
     * @return Список отелей
     */
    List<Hotel> findAllHostels();

    /**
     * Поиск отелей по параметрам
     * @return Список отелей
     */
    List<Hotel> findAllHostelsByParam(String name, String brand, String city, String country, String amenity);

    /**
     * Поиск отелей по Id
     * @return Отель
     */
    Hotel findHostelById(Long id);

    /**
     * Создание нового отеля
     * @return Новый отель
     */
    Hotel createHostel(Hotel hotel);


}
