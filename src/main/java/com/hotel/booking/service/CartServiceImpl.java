package com.hotel.booking.service;


import com.hotel.booking.dto.CartDTO;
import com.hotel.booking.model.CartData;
import java.util.List;

public interface CartServiceImpl {

    List<CartData> getCartByUserId(int userId);

    List<CartData> getCart();

    CartData addToCart(int userId, CartDTO cartDTO);
    CartData addToCartPrimium(int userId, CartDTO cartDTO);


    CartData updateHotelQuantityInCart(int cartId, CartDTO cartDTO);
    CartData updateHotelQuantityInCartPrimium(int cartId, CartDTO cartDTO);

    CartData update(int cartId, int quantity , int price);


    void deleteById(int cartId);

    String emptyCart();

    CartData getCartByCartId(int cartId);
}