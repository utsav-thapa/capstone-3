# Record-shop - E-Commerce Capstone Project 🎵🛒

A full-stack e-commerce application built with Spring Boot backend. 
This project demonstrates building a complete e-commerce platform with authentication, product management and shopping cart functionality,

---
## Project Overview 🎯

This capstone project implements a Record Shop online storefront and implements the Backend API accordingly through Spring Boot REST API and MySQL database.
The project simulates a real-world record store with product browsing by category, featured releases, a shopping cart, user profiles, and authentication.

> Why do records always get invited to parties? Because they know how to drop the beat.

---
## Features

### Phase 1: Foundation & Category Management ✅
-  REST controller setup
-  Category endpoints (GET all, GET by ID)
-  Proper HTTP error handling (404 for not found)

### Phase 2: Product Management 📦
-  Product service integration
-  Get all products endpoint
-  Get featured products only
-  Product filtering and retrieval

### Phase 3: Shopping Cart System 🛒
-  Shopping cart endpoints
-  Add to cart functionality (POST mapping)
-  Authorization checks - only authenticated users can access cart
-  Cart item management
-  Multiple bug fixes for cart operations

### Phase 4: User Profiles 👤
-  Profile controller implementation
-  User profile retrieval (GET mapping)
-  Profile data persistence

---
## API Endpoints

### Authentication 🔐
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration

### Categories 🗂️
- `GET /api/categories` - Get all categories
- `GET /api/categories/:id` - Get category by ID

### Products 📦
- `GET /api/products` - Get all products
- `GET /api/products?featured=true` - Get featured products only
- `GET /api/products/:id` - Get product by ID
- `PUT /api/products/:id` - Update product (Admin only)

### Shopping Cart 🛒
- `GET /api/cart` - Get user's shopping cart
- `POST /api/cart` - Add item to cart
- `DELETE /api/cart/:cartItemId` - Remove item from cart

### User Profile 👤
- `GET /api/profile` - Get current user's profile
- `PUT /api/profile` - Update user's profile
---

## Known Issues & Resolutions

During development, several issues were encountered and resolved:
- Fixed authentication errors returning 403 instead of 401
- Fixed admin-only product operations
- Fixed cart visibility - only authenticated users can see their cart
- Fixed featured products filtering
- Fixed return statements in profile endpoints
- Removed redundant service finality modifiers for better testing
---
## Favorite Code

The following method is my favorite snippet from this project — it adds a product to a user's shopping cart, creating a new cart item or incrementing quantity if the item already exists.
The reason behind this being my favorite code is because of the complexity of the logic in this.

```java
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
```