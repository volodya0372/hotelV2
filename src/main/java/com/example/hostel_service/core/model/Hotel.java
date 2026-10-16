package com.example.hostel_service.core.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class  Hotel {
    Long id;
    String name;
    String description;
    String brand;
    Address address;
    Contacts contacts;
    ArrivalTime arrivalTime;

    Set<String> amenities;
}
