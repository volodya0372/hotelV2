package com.example.hostel_service.core.model;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArrivalTime {
    LocalTime checkIn=LocalTime.of(14,0);
    LocalTime checkOut=LocalTime.of(12,0);
}
