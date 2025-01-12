package com.foodora.Request;

import com.foodora.modal.Address;

import lombok.Data;

@Data
public class OrderRequest {
    
    private Long restaurantId;

    private Address deliveryAddress;
}
