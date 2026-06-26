package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.service.CategoryService;
import org.yearup.service.ProductService;

import java.util.List;

/**
 * REST controller for managing product categories and category-related product queries.
 * This controller exposes simple CRUD operations for categories and a convenience
 * endpoint to list products by category. Authentication/authorization is handled
 * by Spring Security annotations on admin-only endpoints.
 */
@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*")
public class CategoriesController {
    private CategoryService categoryService;
    private ProductService productService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    // return a list of categories and doesn't require authentication
    @GetMapping()
    public List<Category> getAll() {
        // finds and returns all categories from the service layer
        return categoryService.getAllCategories();
    }

    // return the categories using the category id
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        // get the category by id
        Category category = categoryService.getById(id);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //returns all products in a given category
    @GetMapping("{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId) {
        // get a list of product by categoryId
        return productService.listByCategoryId(categoryId);
    }

    //creates new category but only admin can create it
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        // insert the category and return it with status 201 Created
        Category category1 = categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(category1);
    }


    // admin role can update category
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable int id, @RequestBody Category category) {
        // update the category by id and return the updated category (200 OK)

        return categoryService.update(id, category);
    }


    // admin role can delete a category
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
        // delete the category by id and return status 204 No Content
        categoryService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
