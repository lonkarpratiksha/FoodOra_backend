package com.foodora.Request;

import lombok.Data;

@Data
public class UpdateCartItemRequest {
    
    private Long cartItemId;

    private int quantity;
}
