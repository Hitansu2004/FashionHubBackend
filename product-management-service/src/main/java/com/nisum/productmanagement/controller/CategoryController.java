package com.nisum.productmanagement.controller;

import com.nisum.productmanagement.model.Category;
import com.nisum.productmanagement.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        try {

            List<Category> categories = categoryService.getAllCategories();

            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching categories: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable int id) {
        try {
            Optional<Category> category = categoryService.getCategoryById(id);
            if (category.isPresent()) {
                return ResponseEntity.ok(category.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching category: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        try {

            // Validate input
            if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Category name is required");
            }
            
            Category savedCategory = categoryService.createCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating category: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable int id, @RequestBody Category category) {
        try {

            // Validate input
            if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Category name is required");
            }
            
            Optional<Category> updatedCategory = categoryService.updateCategory(id, category);
            if (updatedCategory.isPresent()) {
                return ResponseEntity.ok(updatedCategory.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating category: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {
        try {
            boolean deleted = categoryService.deleteCategory(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting category: " + e.getMessage());
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        try {
            // Test database connection
            categoryService.getAllCategories();
            return ResponseEntity.ok("Category service is running and database is connected");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Category service is running but database connection failed: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getCategoryByName(@RequestParam String name) {
        try {
            Optional<Category> category = categoryService.getCategoryByName(name);
            if (category.isPresent()) {
                return ResponseEntity.ok(category.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error searching category: " + e.getMessage());
        }
    }
}
