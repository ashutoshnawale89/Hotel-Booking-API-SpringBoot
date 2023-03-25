package com.hotel.booking.repository;

import com.hotel.booking.model.OrderData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderData,Integer> {
    @Query("from orderData WHERE userId=:userId  ")
    List<OrderData> findByUserId(int userId);





}
