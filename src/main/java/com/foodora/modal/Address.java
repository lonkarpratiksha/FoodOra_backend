package com.foodora.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;


    private String fullName;
    private String streetAddress;
    private String city;
    private String state;
    private String pincode; 
    private String country;
}
