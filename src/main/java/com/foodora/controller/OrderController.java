package com.foodora.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodora.Request.OrderRequest;
import com.foodora.modal.Order;
import com.foodora.modal.User;
import com.foodora.service.OrderService;
import com.foodora.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api")
public class OrderController {
    
    @Autowired
    private OrderService orderService;


    @Autowired
    private UserService userService;

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest req,
                                                @RequestHeader("Authorization") String jwt)throws Exception{
        
        User user= userService.findUserByJwtToken(jwt);
        Order order= orderService.createOrder(req, user);
        return new ResponseEntity<>(order,HttpStatus.CREATED); 
     
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("Authorization") String jwt)throws Exception{
        
        User user= userService.findUserByJwtToken(jwt);
        List<Order> order= orderService.getUsersOrder(user.getId());
        return new ResponseEntity<>(order,HttpStatus.OK); 
    }

    
    
}
