package com.hotel.booking.service;


import com.hotel.booking.dto.HotelDTO;
import com.hotel.booking.exception.HotelException;
import com.hotel.booking.model.HotelData;
import com.hotel.booking.repository.HotelRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService implements HotelServiceImpl {


    @Autowired
    private HotelRepository hotelRepository;


    @Override
    public HotelData createHotel(HotelDTO hotelDTO) {
        HotelData hotelData = new HotelData(hotelDTO);
        hotelRepository.save(hotelData);
        return hotelData;
    }

    @Override
    public List<HotelData> getAllHotelData() {
        List<HotelData> getHotels= hotelRepository.findAll();
        return getHotels;
    }

    @Override
    public HotelData updateRecordById(HotelDTO hotelDTO, int id) {
        Optional<HotelData> hotelData = hotelRepository.findById(id);
        hotelData.get().setHotelDescription(hotelDTO.getHotelDescription());
        hotelData.get().setHotelImg(hotelDTO.getHotelImg());
        hotelData.get().setHotelName(hotelDTO.getHotelName());
        hotelData.get().setStandardHotelPrice(hotelDTO.getStandardHotelPrice());
        hotelData.get().setPrimiumHotelPrice(hotelDTO.getPrimiumHotelPrice());
        hotelData.get().setRoomQuantity(hotelDTO.getRoomQuantity());
        hotelRepository.save(hotelData.get());
        return hotelData.get();
    }



    @Override
    public HotelData deleteHotelRecord(int hotelId) {
        Optional<HotelData> hotelData = hotelRepository.findById(hotelId);
        if (hotelData.isPresent()) {
            hotelRepository.deleteById(hotelId);
            return hotelData.get();

        } else {
            return null;
        }
    }
    @Override
    public HotelData getHotelModelById(int hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelException("Hotel not found In the List"));
    }
    @Override
    public List<HotelData> sortedListOfHotelInAscendingOrder() {
        List<HotelData> getSortedList=  hotelRepository.findAll(Sort.by(Sort.Direction.ASC,"standardHotelPrice"));
        return getSortedList;
    }

    @Override
    public List<HotelData> sortedListOfHotelInDescendingOrder() {
        List<HotelData> getSortedListInDesc = hotelRepository.findAll(Sort.by(Sort.Direction.DESC,"standardHotelPrice"));
        return getSortedListInDesc;

    }

    @Override
    public int getTotalHotelCount() {
        return hotelRepository.findAll().size();
    }

    @Override
    public List<HotelData> searchByName(String name) {
        String name1 = name.toLowerCase();
        List<HotelData> hotelData = getAllHotelData();
        List<HotelData> collect = hotelData.stream()
                .filter(hotelDataData -> hotelDataData.getHotelName().toLowerCase().contains(name1))
                .collect(Collectors.toList());

        return collect;
    }


    public HotelData updateHotelQuantity(int hotelId, int quantity) {
        HotelData editHotel = hotelRepository.findById(hotelId).orElse(null);
        if (editHotel != null) {
            editHotel.setRoomQuantity(quantity);
            return hotelRepository.save(editHotel);
        }
        return null;
    }
}