package com.hotel.booking.service;


import com.hotel.booking.dto.OrderDTO;
import com.hotel.booking.model.OrderData;
import jakarta.mail.MessagingException;

import java.util.List;

public interface IOrderService {

    String cancelOrder(int orderId, int userId);
    List<OrderData> userOrders(int userId);

    OrderData  placeOrder(int userId, OrderDTO orderDTO) throws MessagingException;

    List<OrderData> getAllOrders();

    int getAllOrdersNumber();



}
