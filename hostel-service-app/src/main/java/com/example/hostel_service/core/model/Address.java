package com.example.hostel_service.core.model;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address {
    Integer houseNumber;
    String street;
    String city;
    String country;
    String postCode;

    public String formatted() {
        return (houseNumber == null ? "" : houseNumber + " ") + safe(street) + ", " + safe(city) + ", " + safe(postCode) + ", " + safe(country);
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }
}
