package com.foodora.Request;

import java.util.List;

import com.foodora.modal.Category;
import com.foodora.modal.IngredientsItem;

import lombok.Data;

@Data
public class CreateFoodRequest {
    
    private String name;
    private String description;
    private Long price;

    private Category category;
    private List<String> images;
    
    private Long restaurantId;
    private boolean vegetarin;
    private boolean seasional;
    private List<IngredientsItem> ingredients;

}
