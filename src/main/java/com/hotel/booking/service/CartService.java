package com.hotel.booking.service;
import com.hotel.booking.dto.CartDTO;
import com.hotel.booking.exception.HotelException;
import com.hotel.booking.model.CartData;
import com.hotel.booking.model.HotelData;
import com.hotel.booking.model.UserData;
import com.hotel.booking.repository.CartRepository;
import com.hotel.booking.repository.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CartService implements CartServiceImpl {


    @Autowired
    private HotelService hotelService;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private UserService userService;

    @Override
    public List<CartData> getCartByUserId(int userId) {
        List<CartData> cartdata = cartRepository.findByUserId(userId);
        if (cartdata.isEmpty())
            throw new HotelException("Cart details with UserId " + userId + " does not exit..!");
        return cartdata;
    }

    @Override
    public List<CartData> getCart() {
        return cartRepository.findAll();
    }


    @Override
    public CartData addToCart(int userId, CartDTO cartDTO) {

        HotelData hotelData = hotelService.getHotelModelById(cartDTO.getHotelId());
        UserData user = userService.getUserDataById(userId);
        List<CartData> cartData = cartRepository.findByUserId(userId);
        int count = 0;
        for (int i = 0; i < cartData.size(); i++) {
            if (cartData.get(i).user.getUserId() == userId && cartData.get(i).hotelData.getHotelId() == cartDTO.getHotelId()) {
                count++;
            }
        }
        if (count == 0) {
            if (cartDTO.getRoomQuantity() <= hotelData.getRoomQuantity()) {
                double cartPrice = (hotelData.getStandardHotelPrice() * cartDTO.getRoomQuantity());
                CartData cart = new CartData(user, hotelData, cartPrice, cartDTO);
                log.info("Item added to cart!");
                System.out.println(cart);
                return cartRepository.save(cart);
            } else throw new HotelException("Hotel Room quantity is not enough!");
        } else {
        throw new HotelException("Hotel Room alredy in cart!");
    }

}


    @Override
    public CartData addToCartPrimium(int userId, CartDTO cartDTO) {

        HotelData hotelData = hotelService.getHotelModelById(cartDTO.getHotelId());
        UserData user = userService.getUserDataById(userId);
        Integer existingDataId = cartRepository.getExistingItemOfCart(cartDTO.getHotelId(), userId);
        if(existingDataId == null && hotelData != null) {
            if (cartDTO.getRoomQuantity() <= hotelData.getRoomQuantity() ) {
                double cartPrice = (hotelData.getPrimiumHotelPrice() * cartDTO.getRoomQuantity());
                CartData cart = new CartData(user, hotelData, cartPrice, cartDTO);
                log.info("Item added to cart!");
                return cartRepository.save(cart);
            }
            else throw new HotelException("Hotel Room quantity is not enough!");
        }
        else {
            CartData updatedCart = this.updateHotelQuantityInCart(existingDataId, cartDTO);
            return updatedCart;
        }
    }

    @Override
    public CartData updateHotelQuantityInCart(int cartId, CartDTO cartDTO) {
        CartData cart = this.getCartByCartId(cartId);
        HotelData hotelData = hotelService.getHotelModelById(cartDTO.hotelId);
        if (cart.getHotel().getRoomQuantity() >= hotelData.getRoomQuantity()) {
            cart.setQuantity(cartDTO.roomQuantity);
            cart.setTotalPrice((hotelData.getStandardHotelPrice() * cartDTO.getRoomQuantity()));
            return cartRepository.save(cart);

            }
        else {
            throw new HotelException("Hotel Room quantity is not enough!");
        }
    }
    @Override
    public CartData updateHotelQuantityInCartPrimium(int cartId, CartDTO cartDTO) {
        CartData cart = this.getCartByCartId(cartId);
        HotelData hotelData = hotelService.getHotelModelById(cartDTO.hotelId);
        if (cart.getHotel().getRoomQuantity() >= hotelData.getRoomQuantity()) {
            cart.setQuantity(cartDTO.roomQuantity);
            cart.setTotalPrice((hotelData.getPrimiumHotelPrice() * cartDTO.getRoomQuantity()));
            return cartRepository.save(cart);

        }
        else {
            throw new HotelException("Hotel Room quantity is not enough!");
        }
    }

    @Override
    public CartData update(int cartId, int quantity,int price){
        CartData cartModel = cartRepository.findById(cartId).get();
        if (cartRepository.findById(cartId).isPresent() && cartModel.hotelData.getRoomQuantity() >= quantity){
               cartModel.setQuantity(quantity);
               if(price == cartModel.getHotel().getStandardHotelPrice()) {
                   cartModel.setTotalPrice(cartModel.getQuantity() * cartModel.getHotel().getStandardHotelPrice());
               } else{
                   cartModel.setTotalPrice(cartModel.getQuantity() * cartModel.getHotel().getPrimiumHotelPrice());
               }
            System.out.println(cartModel);
               return cartRepository.save(cartModel);
        }
        else   throw new HotelException(" Hotel Not found with hotel Id ");
    }


    @Override
    public void deleteById(int cartId){
        CartData cartModel = this.getCartByCartId(cartId);
        cartRepository.delete(cartModel);
    }

    @Override
    public String emptyCart(){
        cartRepository.deleteAll();
        return "Your cart is empty..!";
    }

    @Override
    public CartData getCartByCartId(int cartId) {
        return cartRepository.findByCartId(cartId);
    }

}
