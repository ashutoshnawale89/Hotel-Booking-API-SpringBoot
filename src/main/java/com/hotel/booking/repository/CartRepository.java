package com.hotel.booking.repository;
import com.hotel.booking.model.CartData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartRepository extends JpaRepository<CartData, Integer> {

    @Query("from CartData WHERE cartId=:cartId  ")
    CartData findByCartId(int cartId);

    @Query("from CartData WHERE user.userId=:userId  ")
    List<CartData> findByUserId(int userId);

    @Query(" from CartData WHERE hotelData.hotelId=:hotelId AND user.userId=:userId")
    Integer getExistingItemOfCart(int hotelId, int userId);
}

