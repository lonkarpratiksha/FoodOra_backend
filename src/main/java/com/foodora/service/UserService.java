package com.foodora.service;

import com.foodora.modal.User;


public interface UserService {

    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;
    
    
}
