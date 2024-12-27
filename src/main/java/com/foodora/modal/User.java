package com.foodora.modal;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.foodora.dto.RestaurantDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data    //@Data generates commonly used methods such as getters, setters, toString, equals, hashCode, and constructors.
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  //this will allow us to post password but can't get it in postman
    private String password;
    
    private USER_ROLE role= USER_ROLE.ROLE_CUSTOMER;

    @JsonIgnore //When applied to a field or a method, @JsonIgnore tells Jackson to ignore that field or method during serialization (when converting a Java object to JSON) or deserialization (when converting JSON back to a Java object).
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Order> orders= new ArrayList<>();

    @ElementCollection
    private List<RestaurantDto> favorites = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval =true)     //when we delete user then his all add will be deleted
    private List<Address> addresses=new ArrayList<>();
    
}
