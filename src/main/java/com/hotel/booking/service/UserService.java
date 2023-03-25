package com.hotel.booking.service;


import com.hotel.booking.dto.ResponseDTO;
import com.hotel.booking.dto.UserDTO;
import com.hotel.booking.dto.UserLoginDTO;
import com.hotel.booking.exception.HotelException;
import com.hotel.booking.model.UserData;
import com.hotel.booking.repository.UserRegistrationRepository;
import com.hotel.booking.service.EmailSenderService;
import com.hotel.booking.service.UserServiceImpl;
import com.hotel.booking.util.OtpGenerator;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService implements UserServiceImpl {

    @Autowired
    private OtpGenerator otpGenerator;

    @Autowired
    UserData userData;

    @Autowired
    private UserRegistrationRepository userRepository;


    @Autowired
    private EmailSenderService emailSenderService;


    Long OTP;

    /**
     * @Purpose This method is used to check user is present or not by Id
     */
    @Override
    public UserData getUserDataById(Integer id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new HotelException(("User with Id " + id + " does not exists...")));
    }

    /**
     * @Purpose This method is used to register user
     */
    @Override
    public UserData  registerUser(@Valid UserDTO userDTO) {
        UserData user = userRepository.findUserDataByEmail(userDTO.getEmail());
        if (user == null) {
            userData = new UserData(userDTO);
            userData = userRepository.save(userData);
            System.out.println(userData);
            emailSenderService.sendEmail(userData.getEmail(), "Your Create account succefully. ", userDTO.firstName+"  "+userDTO.lastName );
            return userData;
        } else {
            throw new HotelException("Email already exists",
                    HotelException.ExceptionType.USER_ALREADY_PRESENT);
        }
    }


    /**
     * @Purpose This method is used to login user with
     * correct email and password
     */
    @Override
    public ResponseDTO loginUser(UserLoginDTO userLoginDTO) {
        System.out.println(userLoginDTO.getEmail());
        UserData userDataByEmail = userRepository.findUserDataByEmail(userLoginDTO.getEmail());
        if (userDataByEmail == null) {
            throw new HotelException("Enter registered Email", HotelException.ExceptionType.EMAIL_NOT_FOUND);
        }else {
            if (userDataByEmail.getPassword().equals(userLoginDTO.getPassword())) {
                return new ResponseDTO("Logged in successfully", userDataByEmail);
            } else {
                throw new HotelException("Invalid Password!!!Please Enter Correct Password",
                        HotelException.ExceptionType.PASSWORD_INVALID);
            }
        }

    }



    /**
     * @Purpose This method is used to generate otp for forgot password request
     */
    @Override
    public String forgotPasswordRequest(String email) {
        userData = userRepository.findUserDataByEmail(email);
        if (userData == null) {
            throw new HotelException("User Not Found");
        }
        OTP = otpGenerator.generateOTP();
        String generatedOtp = "Otp is generated \n" + OTP;
        emailSenderService.sendEmail(userData.getEmail(), "Your otp is ", generatedOtp);
        return "Reset Password otp Has Been Sent To Your Email Address " + userData.getEmail();
    }

    /**
     * @Purpose This method is used to reset password
     */
    @Override
    public String resetPassword(String password, Long mailOtp) {
        if (!mailOtp.equals(OTP)) {
            throw new HotelException("Enter Correct OTP");
        }else {
            userData.setPassword(password);
            userRepository.save(userData);
            return "password Reset successfully";
        }
    }



    /**
     * @Purpose This method is used to update the user data
     */
    @Override
    public UserData updateUserData(Integer id, UserDTO userDTO) {
        UserData updateUser = this.getUserDataById(id);
        updateUser.updateUserData(userDTO);
        return userRepository.save(updateUser);
    }

    /**
     * @Purpose This method is used to delete the user data
     */
    @Override
    public void deleteUserData(Integer userId) {
        UserData deleteUser = this.getUserDataById(userId);
        userRepository.delete(deleteUser);
    }

    /**
     * @Purpose This method is used to get list of all user data
     */
    @Override
    public List<UserData> getUserData() {
        return userRepository.findAll();
    }

    @Override
    public int getAllUserNumber() {
        List<UserData> allData=getUserData();
        int count=0;
        for(int i = 0; allData.size() > i; i++){
            count++;
        }
        return count;
    }

}