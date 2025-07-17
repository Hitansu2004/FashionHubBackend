package com.catalogservice.repository;

import com.catalogservice.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
<<<<<<< HEAD

import java.math.BigInteger;
import java.util.List;
=======
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
<<<<<<< HEAD
    List<ProductCategory> findAllByCategoryId(BigInteger categoryId);
=======
    Optional<ProductCategory> findByCategoryId(Long categoryId);
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
    Optional<ProductCategory> findBySku(String sku);
}
