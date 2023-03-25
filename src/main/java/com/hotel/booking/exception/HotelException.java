package com.hotel.booking.exception;

public class HotelException extends RuntimeException {



    public enum ExceptionType {
        EMAIL_NOT_FOUND,
        PASSWORD_INVALID,
        USER_ALREADY_PRESENT,
        BOOK_ALREADY_PRESENT,

        USER_NOT_FOUND,

    }

    public ExceptionType type;


    public HotelException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public HotelException(String message) {
        super(message);
    }
}
