package com.example.hostel_service.app.output.repository;

import com.example.hostel_service.app.output.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity,Long>, JpaSpecificationExecutor<HotelEntity> {}
