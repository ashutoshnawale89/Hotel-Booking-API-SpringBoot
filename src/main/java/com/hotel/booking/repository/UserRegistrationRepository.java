package com.hotel.booking.repository;

import com.hotel.booking.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserData, Integer> {
    UserData findUserDataByEmail(String email);
}
