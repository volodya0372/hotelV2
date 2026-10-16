package com.example.hostel_service.core.service;

import com.example.hostel_service.core.model.Hotel;

import java.util.List;
import java.util.Map;

/**
 * Сервис предназначен для работы с отелями
 */
public interface HotelService {
    /**
     * Поиск всех отелей
     * @return Список отелей
     */
    List<Hotel> findAll();

    /**
     * Поиск отеля по id
     * @param id идентификатор
     * @return Отель
     */
    Hotel getById(Long id);

    /**
     * Создание нового отеля
     * @param hotel - объект с заполнеными данными
     * @return новый отель
     */
    Hotel create(Hotel hotel);

    /**
     * Поиск отеля по любому из параметров
     * @param name - название отеля
     * @param brand - бренд отеля
     * @param city - город отеля
     * @param country - страна отеля
     * @param amenity - дополнительные плюсы отеля
     * @return Список отелей
     */
    List<Hotel> search(String name, String brand, String city, String country, String amenity);

    /**
     * Добавление особенностей отеля
     * @param id - Идентификатор отеля
     * @param amenities - Дополнения
     * @return отель
     */
    Hotel addAmenities(Long id, List<String> amenities);

    /**
     * Группировка отелей по определенному параметру.
     * Возможные параметры: brand, city, country, amenities
     * @param param - Параметр по которому группировать отели
     * @return Сгруппированные отели
     */
    Map<String, Long> histogram(String param);
}
