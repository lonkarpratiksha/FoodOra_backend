package com.foodora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodora.modal.OrderItem;

public interface OrderItemrRepository extends JpaRepository<OrderItem,Long>{
    
}
