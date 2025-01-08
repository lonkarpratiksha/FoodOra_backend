package com.foodora.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.foodora.Request.CreateFoodRequest;
import com.foodora.modal.Category;
import com.foodora.modal.Food;
import com.foodora.modal.Restaurant;

@Service
public interface FoodService {
    
    public Food createFood(CreateFoodRequest req, Category category,Restaurant restaurant);

    public void deleteFood(Long foodId)throws Exception;

    public List<Food> getRestaurantsFood(Long restaurantId,
                                        boolean isVegitarain, 
                                        boolean isNonveg,
                                        boolean isSeasonal,
                                        String foodCategory);

    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailabilityStatus(Long foodId)throws Exception;
    
}
