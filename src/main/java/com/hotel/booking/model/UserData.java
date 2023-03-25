package com.hotel.booking.model;

import com.hotel.booking.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Entity
@Data
@NoArgsConstructor
@Table(name = "user_details")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String email;
    private String password;


    public UserData(Integer userId, String firstName, String lastName, String address,
                    String phoneNumber, String email, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public UserData(UserDTO userDTO) {
        this.updateUserData(userDTO);

    }

    public void updateUserData(UserDTO userDTO) {
        this.firstName = userDTO.firstName;
        this.lastName = userDTO.lastName;
        this.address = userDTO.address;
        this.phoneNumber = userDTO.phoneNumber;
        this.email = userDTO.email;
        this.password = userDTO.password;
    }
}
