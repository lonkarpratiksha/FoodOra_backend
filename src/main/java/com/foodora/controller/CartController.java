package com.foodora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodora.Request.AddCartItemRequest;
import com.foodora.Request.UpdateCartItemRequest;
import com.foodora.modal.Cart;
import com.foodora.modal.CartItem;
import com.foodora.modal.User;
import com.foodora.service.CartService;
import com.foodora.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api")
public class CartController {
    
    @Autowired
    CartService cartService;

    @Autowired
    private UserService userService;

    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(
        @RequestBody AddCartItemRequest req,
        @RequestHeader("Authorization") String jwt
    ) throws Exception{

        CartItem item= cartService.addItemToCart(req, jwt);

        return new ResponseEntity<>(item,HttpStatus.OK);
    }


    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(
        @RequestBody UpdateCartItemRequest req,
        @RequestHeader("Authorization") String jwt
    ) throws Exception{

        CartItem item= cartService.updateCartItemQuantity(req.getCartItemId(),req.getQuantity());

        return new ResponseEntity<>(item,HttpStatus.OK);
    }
    
    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeCartItem(
        @PathVariable Long id,
        @RequestHeader("Authorization") String jwt
    ) throws Exception{

        Cart cart= cartService.removeItemFromCart(id, jwt);

        return new ResponseEntity<>(cart,HttpStatus.OK);
    }


    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(
        @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user= userService.findUserByJwtToken(jwt);
        Cart cart= cartService.clearCart(user.getId());

        return new ResponseEntity<>(cart,HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(
        @RequestHeader("Authorization") String jwt
    ) throws Exception{

        User user= userService.findUserByJwtToken(jwt);
        Cart cart= cartService.findCartByUserId(user.getId());
        return new ResponseEntity<>(cart,HttpStatus.OK);
    }
}
