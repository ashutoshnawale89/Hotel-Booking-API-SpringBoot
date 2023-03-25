package com.hotel.booking.model;

import com.hotel.booking.dto.HotelDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
@Component
@Entity
@Data
@NoArgsConstructor
@Table(name = "hotel_details")
public class HotelData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hotelId;

    private String hotelName;
    private String hotelDescription;
    private String hotelImg;
    private int primiumHotelPrice;
    private int standardHotelPrice;
    private int roomQuantity;

    public HotelData(HotelDTO hotelDTO) {

        this.hotelName = hotelDTO.getHotelName();
        this.hotelDescription = hotelDTO.getHotelDescription();
        this.hotelImg = hotelDTO.getHotelImg();
        this.primiumHotelPrice = hotelDTO.getPrimiumHotelPrice();
        this.standardHotelPrice = hotelDTO.getStandardHotelPrice();
        this.roomQuantity = hotelDTO.getRoomQuantity();
    }
}