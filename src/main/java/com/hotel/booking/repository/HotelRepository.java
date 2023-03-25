package com.hotel.booking.repository;
import com.hotel.booking.model.HotelData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface HotelRepository extends JpaRepository<HotelData, Integer> {

}
