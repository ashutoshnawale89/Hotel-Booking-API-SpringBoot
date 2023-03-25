package com.hotel.booking.service;


import com.hotel.booking.dto.HotelDTO;
import com.hotel.booking.model.HotelData;
import org.springframework.stereotype.Service;

import java.util.List;

public interface HotelServiceImpl {
    HotelData createHotel(HotelDTO bookDTO);

    List<HotelData> getAllHotelData();

    HotelData updateRecordById(HotelDTO bookDTO, int bookId);



    HotelData deleteHotelRecord(int bookId);

    HotelData getHotelModelById(int bookId);

    List<HotelData> sortedListOfHotelInAscendingOrder();

    List<HotelData> sortedListOfHotelInDescendingOrder();


    int getTotalHotelCount();

    List<HotelData> searchByName(String name);
}