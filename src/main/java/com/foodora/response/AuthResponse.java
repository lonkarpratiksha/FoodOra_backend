package com.foodora.response;

import com.foodora.modal.USER_ROLE;

import lombok.Data;

@Data
public class AuthResponse {
    
    private String jwt;

    private String message;

    private USER_ROLE role;
}
