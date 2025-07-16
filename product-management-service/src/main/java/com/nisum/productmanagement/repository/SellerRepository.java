package com.nisum.productmanagement.repository;

import com.nisum.productmanagement.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    
    // Find seller by email
    Optional<Seller> findByEmail(String email);
    
    // Find sellers by seller name (case-insensitive)
    List<Seller> findBySellerNameContainingIgnoreCase(String sellerName);
    
    // Find sellers by contact name (case-insensitive)
    List<Seller> findByContactNameContainingIgnoreCase(String contactName);
    
    // Find sellers by city
    List<Seller> findByCityIgnoreCase(String city);
    
    // Find sellers by state
    List<Seller> findByStateIgnoreCase(String state);
    
    // Find sellers by country
    List<Seller> findByCountryIgnoreCase(String country);
    
    // Check if seller exists by email
    boolean existsByEmail(String email);
    
    // Custom query to search sellers by multiple fields
    @Query("SELECT s FROM Seller s WHERE " +
           "LOWER(s.sellerName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(s.contactName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(s.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(s.city) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(s.state) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(s.country) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Seller> searchSellers(@Param("searchTerm") String searchTerm);
}
