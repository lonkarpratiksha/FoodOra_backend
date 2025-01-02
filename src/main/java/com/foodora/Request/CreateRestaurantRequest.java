package com.foodora.Request;

import java.util.List;

import com.foodora.modal.Address;
import com.foodora.modal.ContactInformation;

import lombok.Data;

@Data
public class CreateRestaurantRequest {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String>images;
}
