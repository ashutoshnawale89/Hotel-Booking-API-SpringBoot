package com.hotel.booking.service;
import com.hotel.booking.dto.OrderDTO;
import com.hotel.booking.exception.HotelException;
import com.hotel.booking.model.CartData;
import com.hotel.booking.model.HotelData;
import com.hotel.booking.model.OrderData;
import com.hotel.booking.model.UserData;
import com.hotel.booking.repository.CartRepository;
import com.hotel.booking.repository.OrderRepository;
import com.hotel.booking.repository.UserRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private UserRegistrationRepository userRepo;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private EmailSenderService mailService;

    @Autowired
    private HotelService bookService;

    List<OrderData> orderList = new ArrayList<>();

    @Override
    public OrderData placeOrder(int userId, OrderDTO orderDTO) {

        List<CartData> cartModel = cartRepository.findByUserId(userId);
        UserData user = userRepo.findById(userId).orElse(null);

        if (!cartModel.isEmpty()) {
            {
                double totalOrderPrice = 0;
                int totalOrderQty = 0;
                List<HotelData> orderedHotels = new ArrayList<>();


                for (int i = 0; i < cartModel.size(); i++) {
                    totalOrderPrice += cartModel.get(i).getTotalPrice();
                    totalOrderQty += cartModel.get(i).getQuantity();
                    orderedHotels.add(cartModel.get(i).getHotel());
                }

                OrderData newOrder = new OrderData(userId, cartModel, orderedHotels, totalOrderQty, totalOrderPrice);
                orderList.add(newOrder);
                orderRepository.save(newOrder);

                mailService.sendEmail(user.getEmail(), "Order Placed",
                        "Order Details:" +
                                "\n" +
                                "\n" + "Order ID ####### " + newOrder.getOrderId() +
                                "\n" + "Order Quantity :" + newOrder.getRoomQuantity() +
                                "\n" + "Order Date :" + newOrder.getOrderDate() +
                                "\n" + "Order Price :" + newOrder.getTotalPrice());

                for (int i = 0; i < cartModel.size(); i++) {
                    HotelData hotel = cartModel.get(i).getHotelData();
                    int updatedQty = hotel.getRoomQuantity() - cartModel.get(i).getQuantity();
                    hotel.setRoomQuantity(updatedQty);
                    cartRepository.deleteById(cartModel.get(i).getCartId());
                }
                return newOrder;
            }
        } else {
            throw (new HotelException("Sorry we cannot placed your Order...! "));
        }
    }

    @Override
    public List<OrderData> getAllOrders() {
        List<OrderData> getOrder = orderRepository.findAll();
        return getOrder;
    }



    @Override
    public List<OrderData> userOrders(int userId) {
        return orderRepository.findByUserId(userId);
    }


    @Override
    public String cancelOrder(int orderId, int userId) {
        OrderData order = orderRepository.findById(orderId).orElse(null);
        List<HotelData> hotel = order.getHotelDataList();
        UserData user = userRepo.findById(userId).orElse(null);
        if (user != null) {

                if (order != null) {
                    order.setCancel(true);
                    mailService.sendEmail(user.getEmail(), "For Cancel Order", "Order Id " + orderId + "\n" + order);
                    orderRepository.save(order);

                    System.out.println(hotel);
                    for (int j = 0; j < orderList.size(); j++) {
                        if (orderList.get(j).getOrderId() == orderId) {
                            for (int i = 0; i < hotel.size(); i++) {

                                    int orderedBookId = orderList.get(j).getCartModel().get(i).getHotel().getHotelId();
                                    int orderedQuantity = orderList.get(j).getCartModel().get(j).getQuantity();
                                    System.out.println(orderedQuantity);
                                    int bookId = hotel.get(i).getHotelId();
                                    if (orderedBookId == bookId) {
                                        HotelData hotel1 = bookService.getHotelModelById(order.getHotelDataList().get(i).getHotelId());
                                        bookService.updateHotelQuantity(hotel1.getHotelId(), hotel1.getRoomQuantity() + orderedQuantity);
                                    }

                            }
                        }
                    }
                    return "Order Cancelled";
                }

            }
        else {
            throw new HotelException("Order is already canceled!");
        }
        return null;
    }

    private static UserData getaNull() {
        return null;
    }

    @Override
    public int getAllOrdersNumber() {
        List<OrderData> getOrder=getAllOrders();
        int count=0;
        for(int i=0; getOrder.size() > i; i++){
            count++;
        }
        return count;
    }




}