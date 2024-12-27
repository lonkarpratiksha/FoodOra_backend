package com.foodora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.foodora.modal.User;

public interface UserRepository extends JpaRepository<User, Long> {
   
 
    public User findByEmail(String username); 
}
