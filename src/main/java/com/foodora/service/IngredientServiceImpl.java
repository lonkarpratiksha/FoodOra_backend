package com.foodora.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodora.modal.IngredientCategory;
import com.foodora.modal.IngredientsItem;
import com.foodora.modal.Restaurant;
import com.foodora.repository.IngredientCategoryRepository;
import com.foodora.repository.IngredientItemRepository;

@Service
public class IngredientServiceImpl implements IngredientsService{

    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant= restaurantService.findRestaurantById(restaurantId);
        
        IngredientCategory category= new IngredientCategory();

        category.setRestaurant(restaurant);

        category.setName(name);


        return ingredientCategoryRepository.save(category);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> opt= ingredientCategoryRepository.findById(id);
        
        if(opt.isEmpty()){
            throw new Exception("Ingredinet categpry not found");
        }

        return opt.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        
        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId)
            throws Exception {

        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);

        IngredientCategory category= findIngredientCategoryById(categoryId);

        IngredientsItem item= new IngredientsItem();
        item.setName(ingredientName);
        item.setCategory(category);
        item.setRestaurant(restaurant);
        item.setInStoke(true);

        IngredientsItem ingredientsItem=ingredientItemRepository.save(item);
        category.getIngredients().add(ingredientsItem);
        return ingredientsItem;
    }

    @Override
    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) {
        return ingredientItemRepository.findByRestaurantId(restaurantId);

    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> optionalIngredientsItem= ingredientItemRepository.findById(id);
        if(optionalIngredientsItem.isEmpty()){
            throw new Exception("Ingredient not found...");
        }
        IngredientsItem ingredientsItem= optionalIngredientsItem.get();
        ingredientsItem.setInStoke(!ingredientsItem.isInStoke());
        
        return ingredientItemRepository.save(ingredientsItem);
    }
    
}
