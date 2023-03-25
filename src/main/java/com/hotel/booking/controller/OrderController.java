package com.hotel.booking.controller;
import com.hotel.booking.dto.OrderDTO;
import com.hotel.booking.dto.ResponseDTO;
import com.hotel.booking.model.OrderData;
import com.hotel.booking.service.IOrderService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin( allowedHeaders = "*", origins = "*")
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService iOrderService;

    /**
     *
     * @param userId
     * @param orderDto
     * place the order using userId
     */
    @PostMapping("/placeOrder/{userId}")
    public ResponseEntity<ResponseDTO> placeOrder(@PathVariable int userId, @RequestBody OrderDTO orderDto) throws MessagingException {
        OrderData order =  iOrderService.placeOrder(userId, orderDto);
        ResponseDTO response = new ResponseDTO("Order Placed", "order Id :"+order.getOrderId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     *
     * @param userId
     * retrieve order data by userId
     */
    @GetMapping("/userOrders/{userId}")
    public ResponseEntity<ResponseDTO> getUserOrders(@PathVariable("userId") int userId){
        List<OrderData> userOrders = iOrderService.userOrders(userId);
        ResponseDTO response = new ResponseDTO("Orders of user ", userOrders);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * To retrieve all orders data
     */
    @GetMapping("/getAllOrder")
    public ResponseEntity<ResponseDTO> getAllOrders(){
        List<OrderData> allOrderData = iOrderService.getAllOrders();
        ResponseDTO responseDTO = new ResponseDTO("Order data:", allOrderData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getToatalOrderNumber")
    public ResponseEntity<ResponseDTO> getAllOrdersNumber(){
        int allOrderData = iOrderService.getAllOrdersNumber();
        ResponseDTO responseDTO = new ResponseDTO("Order data:", allOrderData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    /**
     *
     * @param orderId
     * @param userId
     * cancel the order by using userId and orderId
     */
    @PutMapping("/cancelOrder/{userId}/{orderId}")
    public ResponseEntity<ResponseDTO> cancelOrder(@PathVariable int orderId, @PathVariable int userId) {
        String order = iOrderService.cancelOrder(orderId, userId);
        ResponseDTO response = new ResponseDTO("Order Cancelled ", order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}

