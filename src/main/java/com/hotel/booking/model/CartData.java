package com.hotel.booking.model;

import com.hotel.booking.dto.CartDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@Entity
@Table(name = "cart_details")
public class CartData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

    @ManyToOne
    @JoinColumn(name = "hotelId")
    public HotelData hotelData;

    @OneToOne
    @JoinColumn(name = "userId")
    public UserData user;

    private int roomQuantity;

    public CartData() {

    }

    public CartData(UserData user, HotelData hotelData, double cartPrice , CartDTO cartDTO) {
        this.hotelData=hotelData;
        this.user=user;
        this.roomQuantity=cartDTO.getRoomQuantity();
        this.totalPrice=cartPrice;
        System.out.println(cartPrice);

    }


    public int getQuantity() {
        return roomQuantity;
    }

    public void setQuantity(int roomQuantity) {
        this.roomQuantity = roomQuantity;
    }

    private double totalPrice;

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public HotelData getHotel() {
        return hotelData;
    }

    public void setHotel(HotelData book) {
        this.hotelData = hotelData;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }



    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


}