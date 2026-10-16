package com.example.hostel_service.core.service;

import com.example.hostel_service.core.model.Hotel;
import com.example.hostel_service.core.storage.HotelStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelServiceImplTest {

    @Mock
    HotelStorage storage;

    @InjectMocks
    HotelServiceImpl hotelService;

    Hotel sampleHotel;

    @BeforeEach
    void setUp() {
        sampleHotel = new Hotel();
        sampleHotel.setId(1L);
        sampleHotel.setName("Test Hotel");
        sampleHotel.setBrand("Hilton");
        sampleHotel.setAmenities(new LinkedHashSet<>(List.of("WiFi", "Pool")));

        var address = new com.example.hostel_service.core.model.Address();
        address.setCity("Minsk");
        address.setCountry("Belarus");
        sampleHotel.setAddress(address);
    }

    @Test
    @DisplayName("findAll должен возвращать список всех отелей из хранилища")
    void findAll_ShouldReturnListOfHotels() {
        List<Hotel> expectedHotels = List.of(sampleHotel);
        when(storage.findAllHostels()).thenReturn(expectedHotels);

        List<Hotel> actualHotels = hotelService.findAll();

        assertNotNull(actualHotels);
        assertEquals(1, actualHotels.size());
        assertEquals("Test Hotel", actualHotels.get(0).getName());
        verify(storage, times(1)).findAllHostels();
    }

    @Test
    @DisplayName("getById должен возвращать отель по его идентификатору")
    void getById_ShouldReturnHotel_WhenIdExists() {
        when(storage.findHostelById(1L)).thenReturn(sampleHotel);

        Hotel actualHotel = hotelService.getById(1L);

        assertNotNull(actualHotel);
        assertEquals(1L, actualHotel.getId());
        assertEquals("Test Hotel", actualHotel.getName());
        verify(storage, times(1)).findHostelById(1L);
    }

    @Test
    @DisplayName("create должен сохранять и возвращать новый отель")
    void create_ShouldSaveAndReturnHotel() {
        when(storage.createHostel(sampleHotel)).thenReturn(sampleHotel);

        Hotel createdHotel = hotelService.create(sampleHotel);

        assertNotNull(createdHotel);
        assertEquals("Test Hotel", createdHotel.getName());
        verify(storage, times(1)).createHostel(sampleHotel);
    }

    @Test
    @DisplayName("addAmenities должен добавлять новые удобства к существующим и сохранять отель")
    void addAmenities_ShouldAddAmenitiesAndSave() {
        when(storage.findHostelById(1L)).thenReturn(sampleHotel);
        when(storage.createHostel(sampleHotel)).thenReturn(sampleHotel);

        List<String> newAmenities = List.of("Parking", "Gym");
        Hotel updatedHotel = hotelService.addAmenities(1L, newAmenities);

        assertNotNull(updatedHotel);
        assertTrue(updatedHotel.getAmenities().contains("WiFi"));
        assertTrue(updatedHotel.getAmenities().contains("Parking"));
        assertTrue(updatedHotel.getAmenities().contains("Gym"));
        assertEquals(4, updatedHotel.getAmenities().size());

        verify(storage, times(1)).findHostelById(1L);
        verify(storage, times(1)).createHostel(sampleHotel);
    }

    @Test
    @DisplayName("search должен вызывать параметризованный поиск в хранилище")
    void search_ShouldCallStorageWithProperParams() {
        List<Hotel> expectedHotels = List.of(sampleHotel);
        when(storage.findAllHostelsByParam("Name", "Brand", "City", "Country", "Amenity"))
                .thenReturn(expectedHotels);

        List<Hotel> actualHotels = hotelService.search("Name", "Brand", "City", "Country", "Amenity");

        assertNotNull(actualHotels);
        assertEquals(1, actualHotels.size());
        verify(storage, times(1)).findAllHostelsByParam("Name", "Brand", "City", "Country", "Amenity");
    }

    @Test
    @DisplayName("histogram должен выбрасывать исключение при передаче невалидного параметра")
    void histogram_ShouldThrowException_WhenParamIsInvalid() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                hotelService.histogram("invalid_param")
        );

        assertTrue(exception.getMessage().contains("Неподдерживающий параметр: invalid_param"));
        verifyNoInteractions(storage);
    }

    @Test
    @DisplayName("histogram должен корректно группировать по брендам (без учета регистра букв)")
    void histogram_ShouldReturnCorrectBrandCounts_CaseInsensitive() {
        Hotel hotel2 = new Hotel();
        hotel2.setBrand("hilton"); // Другой регистр
        Hotel hotel3 = new Hotel();
        hotel3.setBrand("Marriott");

        when(storage.findAllHostels()).thenReturn(List.of(sampleHotel, hotel2, hotel3));

        Map<String, Long> histogram = hotelService.histogram("brand");

        assertEquals(2, histogram.size());
        assertTrue(histogram.containsKey("Hilton") || histogram.containsKey("hilton"));
        assertEquals(2L, histogram.get("Hilton") != null ? histogram.get("Hilton") : histogram.get("hilton"));
        assertEquals(1L, histogram.get("Marriott"));
    }

    @Test
    @DisplayName("histogram должен корректно считать вложенную коллекцию удобств")
    void histogram_ShouldReturnCorrectAmenitiesCounts() {
        Hotel hotel2 = new Hotel();
        hotel2.setAmenities(new LinkedHashSet<>(List.of("WiFi", "Parking")));

        when(storage.findAllHostels()).thenReturn(List.of(sampleHotel, hotel2));

        Map<String, Long> histogram = hotelService.histogram("amenities");

        assertEquals(3, histogram.size());
        assertEquals(2L, histogram.get("WiFi"));
        assertEquals(1L, histogram.get("Pool"));
        assertEquals(1L, histogram.get("Parking"));
    }

    @Test
    @DisplayName("histogram по городам не должен падать или учитывать записи с null-адресом")
    void histogram_ShouldIgnoreNullAddresses_WhenGroupingByCity() {
        Hotel hotelWithoutAddress = new Hotel();
        hotelWithoutAddress.setAddress(null);

        when(storage.findAllHostels()).thenReturn(List.of(sampleHotel, hotelWithoutAddress));

        Map<String, Long> histogram = hotelService.histogram("city");

        assertEquals(1, histogram.size());
        assertEquals(1L, histogram.get("Minsk"));
    }
}
