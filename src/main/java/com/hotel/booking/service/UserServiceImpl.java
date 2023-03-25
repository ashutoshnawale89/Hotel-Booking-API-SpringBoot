package com.hotel.booking.service;


import com.hotel.booking.dto.ResponseDTO;
import com.hotel.booking.dto.UserDTO;
import com.hotel.booking.dto.UserLoginDTO;
import com.hotel.booking.model.UserData;
import jakarta.validation.Valid;

import java.util.List;

public interface UserServiceImpl {

    UserData updateUserData(Integer id, UserDTO userDTO);

    UserData getUserDataById(Integer id);

    void deleteUserData(Integer userId);

    List<UserData> getUserData();

    ResponseDTO loginUser(UserLoginDTO userLoginDTO);



    UserData  registerUser(@Valid UserDTO userDTO);

    String forgotPasswordRequest(String email);

    String resetPassword(String password, Long otp);

    int getAllUserNumber();
}
