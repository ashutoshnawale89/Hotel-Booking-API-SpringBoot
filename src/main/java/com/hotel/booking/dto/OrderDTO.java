package com.hotel.booking.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private int roomQuantity;
    private int hotelId;
    private int cartId;
}

