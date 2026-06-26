package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;
import org.yearup.service.ShoppingCartService;
import org.yearup.service.UserService;

import java.security.Principal;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
    }

    // retrieves the current user's shopping cart
    @GetMapping
    public ShoppingCart getCart(Principal principal) {
        // gets the currently logged in username
        String userName = principal.getName();

        // find database user by username
        User user = userService.getByUserName(userName);
        int userId = user.getId();

        // returns cart associated with the user
        return shoppingCartService.getByUserId(userId);
    }

    // adds a product to the current user's cart
    @PostMapping("/products/{id}")
    public ResponseEntity<ShoppingCart> addProduct(Principal principal, @PathVariable int id) {


        String userName = principal.getName();

        User user = userService.getByUserName(userName);
        int userId = user.getId();

        return ResponseEntity.status(HttpStatus.CREATED).body(shoppingCartService.addProduct(id, userId));
    }

    // updates quantity of an existing product in the cart
    @PutMapping("/products/{id}")
    public ResponseEntity<ShoppingCart> updateCart(Principal principal, @PathVariable int id, @RequestBody ShoppingCartItem item) {
        String userName = principal.getName();

        User user = userService.getByUserName(userName);
        int userId = user.getId();

        return ResponseEntity.status(HttpStatus.OK).body(shoppingCartService.updateCartItem(userId, id, item));
    }

    // find database user by username
    @DeleteMapping
    public ResponseEntity<ShoppingCart> clearCart(Principal principal) {

        String userName = principal.getName();

        User user = userService.getByUserName(userName);
        int userId = user.getId();
        shoppingCartService.deleteCart(userId);

        return ResponseEntity.status(HttpStatus.OK).body(shoppingCartService.getByUserId(userId));
    }

}
