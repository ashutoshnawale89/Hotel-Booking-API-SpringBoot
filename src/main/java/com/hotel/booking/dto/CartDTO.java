package com.hotel.booking.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    @NonNull
    public int hotelId;
    @NonNull
    public int roomQuantity;
}


