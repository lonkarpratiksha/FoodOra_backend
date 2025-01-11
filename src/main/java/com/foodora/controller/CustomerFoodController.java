package com.foodora.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodora.modal.Food;
import com.foodora.modal.User;
import com.foodora.service.FoodService;
// import com.foodora.service.RestaurantService;
import com.foodora.service.UserService;


@RestController
@RequestMapping("/api/food")
public class CustomerFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    // @Autowired
    // private RestaurantService restaurantService;

    @GetMapping("/search")
    private ResponseEntity<List<Food>> searchFood(@RequestParam String name,
                                            @RequestHeader("Authorization") String jwt) throws Exception{
        User user= userService.findUserByJwtToken(jwt);
        List<Food> foods= foodService.searchFood(name);

        return new ResponseEntity<>(foods,HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/{restaurantId}")
    private ResponseEntity<List<Food>> getRestaurantFood(@RequestParam boolean vegetarian,
                                            @RequestParam boolean seasonal,
                                            @RequestParam boolean nonveg,
                                            @PathVariable Long restaurantId,
                                            @RequestParam (required=false) String food_category,
                                            @RequestHeader("Authorization") String jwt) throws Exception{
        User user= userService.findUserByJwtToken(jwt);
        List<Food> foods= foodService.getRestaurantsFood(restaurantId,vegetarian,nonveg,seasonal,food_category);

        return new ResponseEntity<>(foods,HttpStatus.CREATED);
    }
}
