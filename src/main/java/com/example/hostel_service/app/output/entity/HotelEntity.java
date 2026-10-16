package com.example.hostel_service.app.output.entity;

import com.example.hostel_service.core.model.Address;
import com.example.hostel_service.core.model.ArrivalTime;
import com.example.hostel_service.core.model.Contacts;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "hotels")
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String name;
    @Column(length = 3000)
    String description;
    String brand;
    @Embedded
    Address address;
    @Embedded
    Contacts contacts;
    @Embedded
    ArrivalTime arrivalTime = new ArrivalTime();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "hotel_amenities", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "amenity")
    Set<String> amenities = new LinkedHashSet<>();
}
