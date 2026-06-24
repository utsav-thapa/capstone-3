package org.yearup.service;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartService
{
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService)
    {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    public ShoppingCart getByUserId(int userId)
    {
        ShoppingCart cart = new ShoppingCart();
        List<CartItem> cartItems = shoppingCartRepository.findByUserId(userId);

        // load the user's cart rows, look up each product, and build the ShoppingCart
        for (CartItem cartItem : cartItems){
            Product product = productService.getById(cartItem.getProductId());
            ShoppingCartItem item = new ShoppingCartItem();
            item.setProduct(product);
            item.setQuantity(cartItem.getQuantity());
//            cart.getItems().put(product.getProductId(),item);
            cart.add(item);
        }
        return cart;
    }

    public ShoppingCart addProduct(int productId, int userId) {
        CartItem newItem = new CartItem();
        newItem.setUserId(userId);
        newItem.setProductId(productId);

        CartItem existingItem = shoppingCartRepository.findByUserIdAndProductId(userId,productId);

        if (existingItem == null){
            shoppingCartRepository.save(newItem);

        } else {
//            newItem.setProductId(productId);
//            newItem.setUserId(userId);
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            shoppingCartRepository.save(existingItem);
        }
        return getByUserId(userId);
    }

    @Transactional
    public void deleteCart(int userId) {
        shoppingCartRepository.deleteByUserId(userId);
    }


    // add additional methods here
}
