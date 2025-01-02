package com.foodora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodora.modal.Address;

public interface AddressRepository extends JpaRepository<Address,Long>{
    
}
