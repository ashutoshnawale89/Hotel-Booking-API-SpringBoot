package com.hotel.booking.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Entity(name = "orderData")
public class OrderData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @CreationTimestamp
    private LocalDate orderDate;
    private int roomQuantity;
    private double totalPrice;

    private int userId;
    @ManyToMany
    @JoinColumn(name = "hotelId")
    private List<HotelData> hotelDataList;

    @OneToMany(fetch = FetchType.LAZY,orphanRemoval=true)
    @org.hibernate.annotations.ForeignKey(name = "none")
    private List<CartData> cartModel;

    private boolean cancel;



    public OrderData() {

    }

    public OrderData(int userId, List<CartData> cartModel, List<HotelData> orderedHotelDataList, int totalOrderQty, double totalOrderPrice) {
        this.userId=userId;
        this.roomQuantity = totalOrderQty;
        this.hotelDataList = orderedHotelDataList;
        this.cartModel = cartModel;
        this.totalPrice = totalOrderPrice;
    }
}

