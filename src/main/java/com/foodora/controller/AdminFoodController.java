package com.foodora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodora.Request.CreateFoodRequest;
import com.foodora.modal.Food;
import com.foodora.modal.Restaurant;
import com.foodora.modal.User;
import com.foodora.response.MessageRespose;
import com.foodora.service.FoodService;
import com.foodora.service.RestaurantService;
import com.foodora.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {
    
    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    private ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                            @RequestHeader("Authorization") String jwt) throws Exception{
        User user= userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.findRestaurantById(req.getRestaurantId());
        Food food= foodService.createFood(req, req.getCategory(), restaurant);

        return new ResponseEntity<>(food,HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    private ResponseEntity<MessageRespose> deleteFood(@PathVariable Long id,
                                            @RequestHeader("Authorization") String jwt) throws Exception{
        User user= userService.findUserByJwtToken(jwt);
        foodService.deleteFood(id);

        MessageRespose res= new MessageRespose();
        res.setMessage("Food deleted succesfully...");
        return new ResponseEntity<>(res,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Food> updateFoodAvailabilityStatus(@PathVariable Long id,
                                            @RequestHeader("Authorization") String jwt) throws Exception{
        User user= userService.findUserByJwtToken(jwt);
        Food food=foodService.updateAvailabilityStatus(id);
        
        return new ResponseEntity<>(food,HttpStatus.CREATED);
    }
}
