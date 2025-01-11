package com.foodora.Request;

import lombok.Data;

@Data
public class IngredientItemRequest {
    

    private Long restaurantId;
    private String ingredientName;
    private Long categoryId;
}
