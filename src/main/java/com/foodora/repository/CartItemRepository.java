package com.foodora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodora.modal.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem,Long>{

    
}
