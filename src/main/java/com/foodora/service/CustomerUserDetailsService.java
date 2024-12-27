package com.foodora.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.foodora.modal.USER_ROLE;
import com.foodora.modal.User;
import com.foodora.repository.UserRepository;

@Service
public class CustomerUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepository.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("user not found with email"+username);
        }

        USER_ROLE role = user.getRole();
        List<GrantedAuthority> authorities= new ArrayList<>();
        
        authorities.add(new SimpleGrantedAuthority(role.toString()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }
    
}

// Flow Summary
// Fetch User: The method retrieves a User object from the database using the provided username (email).
// Validation: If the user does not exist, a UsernameNotFoundException is thrown.
// Role Assignment: The user's role is converted to GrantedAuthority and added to the authorities list.
// UserDetails Creation: A Spring Security User object is created with the email, password, and authorities.
// Return: The UserDetails object is returned to Spring Security, which uses it to complete the authentication process.
