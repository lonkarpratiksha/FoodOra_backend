package com.foodora.repository;

import com.foodora.modal.Restaurant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long>{

    @Query("SELECT r FROM Restaurant r WHERE lower(r.name) LIKE lower(concat('%',:query,'%')) " +
    "OR lower(r.cuisineType) LIKE lower(concat('%',:query,'%'))")
    
    List<Restaurant> findBySearchQuery(String query);
    Restaurant findByOwnerId(Long userId);
}
