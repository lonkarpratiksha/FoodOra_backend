package com.foodora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodora.modal.IngredientCategory;
import java.util.List;


public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory,Long>{
    
    List<IngredientCategory> findByRestaurantId(Long id);
}
