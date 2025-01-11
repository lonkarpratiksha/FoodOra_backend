package com.foodora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodora.modal.Cart;

public interface CartRepository  extends JpaRepository<Cart,Long>{

    public Cart findByCustomerId(Long userId);
}
