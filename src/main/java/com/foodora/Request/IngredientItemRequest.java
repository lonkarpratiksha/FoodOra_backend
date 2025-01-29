package com.foodora.Request;

import lombok.Data;

@Data
public class IngredientItemRequest {
    

    private Long restaurantId;
    private String name;
    private Long categoryId;
}
