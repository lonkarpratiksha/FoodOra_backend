package com.foodora.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodora.Request.LoginRequest;
import com.foodora.config.JwtProvider;
import com.foodora.modal.Cart;
import com.foodora.modal.USER_ROLE;
import com.foodora.modal.User;
import com.foodora.repository.CartRepository;
import com.foodora.repository.UserRepository;
import com.foodora.response.AuthResponse;
import com.foodora.service.CustomerUserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private CartRepository cartRepository;

    //sign up method
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user){
        User isEmailExist= userRepository.findByEmail(user.getEmail());
        if(isEmailExist!=null){
            throw new RuntimeException("Email is already used with another account");
        }


        User createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser= userRepository.save(createdUser);

        Cart cart= new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        Authentication authentication= new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt= jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Registered Successfully!!!");
        authResponse.setRole(savedUser.getRole());

        return new ResponseEntity<>(authResponse,HttpStatus.CREATED);

        // Summary
        // Checks if the email is already registered.
        // Creates a new user, saves them in the database, and assigns them a cart.
        // Authenticates the user and generates a JWT.
        // Returns a success response containing the JWT and other details.
    }

    //login 
    @PostMapping("/signin")       
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest request){

        String username=request.getEmail();
        String password=request.getPassword();

        Authentication authentication= authenticate(username,password);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        String jwt= jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login Successfully!!!");
        authResponse.setRole(USER_ROLE.valueOf(role));

        return new ResponseEntity<>(authResponse,HttpStatus.OK);

        // Extracts User Credentials: Takes the email (username) and password from the login request.
        // Authenticates the User: Calls the authenticate method to verify the credentials against stored data. Throws an exception if the credentials are invalid.
        // Fetches User Role: Retrieves the user's assigned role from the authentication object. If no roles are found, it sets the role as null.
        // Generates JWT Token: Uses the JwtProvider class to create a JWT token that includes user information (email, role) as claims.
        // Prepares Response: Includes the JWT token.,Adds a success message.,Specifies the user's role.
        // Returns Response: Sends an HTTP response with the token, message, and role, along with a status of 200 (OK) to indicate a successful login.
    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("Invalid username....");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password....");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());

        // Summary of Functionality
        // Input: Takes a username and password as input.
        // Process:
        // Fetches user details from the data source.
        // Validates the provided password against the stored one.
        // Output: Returns an Authentication object if the credentials are valid.
        // Error Handling: Throws a BadCredentialsException for invalid usernames or passwords.
    }


}
