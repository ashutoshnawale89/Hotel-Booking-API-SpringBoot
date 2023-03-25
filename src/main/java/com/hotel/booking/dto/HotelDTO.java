package com.hotel.booking.dto;

import lombok.Data;
import lombok.ToString;

@Data
public @ToString class HotelDTO {

    private String hotelName;
    private String hotelDescription;
    private String hotelImg;
    private Integer primiumHotelPrice;
    private Integer standardHotelPrice;
    private Integer roomQuantity;

}
