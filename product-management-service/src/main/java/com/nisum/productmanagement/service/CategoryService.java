package com.nisum.productmanagement.service;

import com.nisum.productmanagement.model.Category;
import com.nisum.productmanagement.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        try {
            List<Category> categories = categoryRepository.findAll();
            return categories;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch categories from database", e);
        }
    }

    public Optional<Category> getCategoryById(int id) {
        try {
            Optional<Category> category = categoryRepository.findById(id);
            return category;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch category by ID", e);
        }
    }

    public Optional<Category> getCategoryByName(String name) {
        try {
            Optional<Category> category = categoryRepository.findByCategoryNameIgnoreCase(name);
            return category;
        } catch (Exception e) {
            throw new RuntimeException("Failed to search category by name", e);
        }
    }

    public Category createCategory(Category category) {
        try {
            // Validate input
            if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
                throw new IllegalArgumentException("Category name cannot be null or empty");
            }
            
            // Check if category with the same name already exists
            if (categoryRepository.existsByCategoryNameIgnoreCase(category.getCategoryName())) {
                throw new RuntimeException("A category with name '" + category.getCategoryName() + "' already exists");
            }
            
            Category savedCategory = categoryRepository.save(category);
            return savedCategory;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create category", e);
        }
    }
    
    public Optional<Category> updateCategory(int id, Category updatedCategory) {
        try {

            // Validate input
            if (updatedCategory.getCategoryName() == null || updatedCategory.getCategoryName().trim().isEmpty()) {
                throw new IllegalArgumentException("Category name cannot be null or empty");
            }
            
            Optional<Category> existingCategory = categoryRepository.findById(id);
            
            if (existingCategory.isPresent()) {
                // Check if another category with the same name already exists (but not the one we're updating)
                Optional<Category> categoryWithSameName = categoryRepository.findByCategoryNameIgnoreCase(updatedCategory.getCategoryName());
                if (categoryWithSameName.isPresent() && categoryWithSameName.get().getId() != id) {
                    throw new RuntimeException("Another category with name '" + updatedCategory.getCategoryName() + "' already exists");
                }
                
                Category category = existingCategory.get();
                category.setCategoryName(updatedCategory.getCategoryName());
                category.setDescription(updatedCategory.getDescription());
                category.setCategoryImage(updatedCategory.getCategoryImage());
                
                Category savedCategory = categoryRepository.save(category);
                return Optional.of(savedCategory);
            } else {
                return Optional.empty();
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update category", e);
        }
    }
    
    public boolean deleteCategory(int id) {
        try {
            if (categoryRepository.existsById(id)) {
                categoryRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete category", e);
        }
    }
}
