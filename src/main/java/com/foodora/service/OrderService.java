package com.foodora.service;

import java.util.List;

import com.foodora.Request.OrderRequest;
import com.foodora.modal.Order;
import com.foodora.modal.User;

public interface OrderService {

    public Order createOrder(OrderRequest order, User user)throws Exception;

    public Order updateOrder(Long orderId,String OrderStatus) throws Exception;

    public void cancelOrder(Long orderId)throws Exception;
    
    public List<Order> getUsersOrder(Long userId)throws Exception;

    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus)throws Exception;

    public Order findOrderById(Long orderId)throws Exception;
    
}
