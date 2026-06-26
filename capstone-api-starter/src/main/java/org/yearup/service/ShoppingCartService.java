package org.yearup.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    // retrieves the full shopping cart for a given user
    public ShoppingCart getByUserId(int userId) {
        ShoppingCart cart = new ShoppingCart();
        List<CartItem> cartItems = shoppingCartRepository.findByUserId(userId);

        // load the user's cart rows, look up each product, and build the ShoppingCart
        for (CartItem cartItem : cartItems) {
            Product product = productService.getById(cartItem.getProductId());
            ShoppingCartItem item = new ShoppingCartItem();
            item.setProduct(product);
            item.setQuantity(cartItem.getQuantity());
            cart.add(item);
        }
        return cart;
    }

    // adds a product to the user's cart or increments quantity if it already exists
    public ShoppingCart addProduct(int productId, int userId) {
        CartItem newItem = new CartItem();
        newItem.setUserId(userId);
        newItem.setProductId(productId);

        CartItem existingItem = shoppingCartRepository.findByUserIdAndProductId(userId, productId);

        if (existingItem == null) {
            shoppingCartRepository.save(newItem);

        } else {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            shoppingCartRepository.save(existingItem);
        }
        return getByUserId(userId);
    }

    // deletes all items from a user's cart
    @Transactional
    public void deleteCart(int userId) {
        shoppingCartRepository.deleteByUserId(userId);
    }

    // updates quantity of a specific product in the cart
    public ShoppingCart updateCartItem(int userId, int id, ShoppingCartItem item) {
        CartItem cartItem = shoppingCartRepository.findByUserIdAndProductId(userId, id);
        cartItem.setQuantity(item.getQuantity());
        shoppingCartRepository.save(cartItem);
        return getByUserId(userId);
    }

}
