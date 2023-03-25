package com.hotel.booking.controller;


import com.hotel.booking.dto.HotelDTO;
import com.hotel.booking.dto.ResponseDTO;
import com.hotel.booking.model.HotelData;
import com.hotel.booking.service.HotelServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin( allowedHeaders = "*", origins = "*")
@RequestMapping("/hotel")
@RestController
public class HotelController {

    @Autowired
    private HotelServiceImpl hotelService;


    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllHotelData() {
        List<HotelData> listOfHotels = hotelService.getAllHotelData();
        ResponseDTO responseDto = new ResponseDTO("Data retrieved successfully :", listOfHotels);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @GetMapping("/get/{hotelId}")
    public ResponseEntity<ResponseDTO> getBookModelById(@PathVariable int hotelId) {
        HotelData hotelModel = hotelService.getHotelModelById(hotelId);
        ResponseDTO responseDto = new ResponseDTO("Success Call for Hotel Id!!!", hotelModel);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/searchByName/{name}")
    public ResponseEntity<ResponseDTO> searchByName(@PathVariable String name ) {
        List<HotelData> hotelDataDataList = hotelService.searchByName(name);
        ResponseDTO respDTO = new ResponseDTO("Hotes are ....", hotelDataDataList);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }

    /**
     * @Purpose : To show total Hotel Count in hotel booking app
     */
    @GetMapping("/totalHotelCount")
    public ResponseEntity<ResponseDTO> getTotalHotelCount() {
        int totalCount = hotelService.getTotalHotelCount();
        return new ResponseEntity<>(new ResponseDTO("Total books are  : ", totalCount), HttpStatus.OK);
    }

    @PostMapping("/addhotel")
    public ResponseEntity<ResponseDTO> addUserInBookStore(@RequestBody HotelDTO hotelDto) {
        HotelData newHotel = hotelService.createHotel(hotelDto);
        ResponseDTO responseDto = new ResponseDTO("Created the new book in book store", newHotel);
        return new ResponseEntity(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/updatehoteById/{hotelId}")
    public ResponseEntity<ResponseDTO> updateRecordById(@PathVariable int hotelId, @Valid @RequestBody HotelDTO hotelDto) {
        HotelData updateRecord = hotelService.updateRecordById(hotelDto,hotelId);
        ResponseDTO responseDto = new ResponseDTO(" Book Record updated successfully by Id", updateRecord);
        return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deletehotel/{hotelId}")
    public ResponseEntity<ResponseDTO> deleteRecordById(@PathVariable int hotelId) {
        HotelData hotelModel = hotelService.deleteHotelRecord(hotelId);
        ResponseDTO responseDto = new ResponseDTO("Record deleted successfully !", hotelModel);
        return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/sortAsc")
    public ResponseEntity<ResponseDTO> getBooksInAscendingOrder() {
        List<HotelData> listOfHotels = hotelService.sortedListOfHotelInAscendingOrder();
        ResponseDTO responseDto = new ResponseDTO("Data retrieved successfully :", listOfHotels);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    @GetMapping("/sortDesc")
    public ResponseEntity<ResponseDTO> getBooksInDescendingOrder() {
        List<HotelData> listOfHotels = hotelService.sortedListOfHotelInDescendingOrder();
        ResponseDTO resposnseDto = new ResponseDTO("Data retrieved successfully :", listOfHotels);
        return new ResponseEntity(resposnseDto, HttpStatus.OK);

    }
}