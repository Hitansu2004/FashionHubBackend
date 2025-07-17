package com.nisum.productmanagement.service;

import com.nisum.productmanagement.dto.SellerDto;
import com.nisum.productmanagement.exception.SellerNotFoundException;
import com.nisum.productmanagement.model.Seller;
import com.nisum.productmanagement.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    public List<SellerDto> getAllSellers() {
        List<Seller> sellers = sellerRepository.findAll();
        return sellers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public SellerDto getSellerById(Long id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with id: " + id));
        return convertToDto(seller);
    }

    @Transactional
    public SellerDto createSeller(SellerDto sellerDto) {
        try {
            // Validate required fields
            if (sellerDto.getSellerName() == null || sellerDto.getSellerName().trim().isEmpty()) {
                throw new IllegalArgumentException("Seller name cannot be null or empty");
            }
            if (sellerDto.getContactName() == null || sellerDto.getContactName().trim().isEmpty()) {
                throw new IllegalArgumentException("Contact name cannot be null or empty");
            }
            
            // Validate email uniqueness if provided
            if (sellerDto.getEmail() != null && !sellerDto.getEmail().trim().isEmpty()) {
                if (sellerRepository.existsByEmail(sellerDto.getEmail().trim())) {
                    throw new IllegalArgumentException("A seller with this email already exists");
                }
            }
            
            Seller seller = convertToEntity(sellerDto);
            Seller savedSeller = sellerRepository.save(seller);
            return convertToDto(savedSeller);
            
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create seller: " + e.getMessage(), e);
        }
    }

    @Transactional
    public SellerDto updateSeller(Long id, SellerDto sellerDto) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with id: " + id));

        // Validate required fields
        if (sellerDto.getSellerName() == null || sellerDto.getSellerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Seller name cannot be null or empty");
        }
        if (sellerDto.getContactName() == null || sellerDto.getContactName().trim().isEmpty()) {
            throw new IllegalArgumentException("Contact name cannot be null or empty");
        }

        // Validate email uniqueness if changed
        if (sellerDto.getEmail() != null && !sellerDto.getEmail().trim().isEmpty()) {
            String newEmail = sellerDto.getEmail().trim();
            if (!newEmail.equals(seller.getEmail()) && sellerRepository.existsByEmail(newEmail)) {
                throw new IllegalArgumentException("A seller with this email already exists");
            }
        }

        seller.setSellerName(sellerDto.getSellerName().trim());
        seller.setContactName(sellerDto.getContactName().trim());
        seller.setEmail(sellerDto.getEmail() != null ? sellerDto.getEmail().trim() : null);
        seller.setPhoneNumber(sellerDto.getPhoneNumber() != null ? sellerDto.getPhoneNumber().trim() : null);
        seller.setAddressLine1(sellerDto.getAddressLine1() != null ? sellerDto.getAddressLine1().trim() : 
                              (sellerDto.getAddress() != null ? sellerDto.getAddress().trim() : null));
        seller.setAddressLine2(sellerDto.getAddressLine2() != null ? sellerDto.getAddressLine2().trim() : null);
        seller.setCity(sellerDto.getCity() != null ? sellerDto.getCity().trim() : null);
        seller.setState(sellerDto.getState() != null ? sellerDto.getState().trim() : null);
        seller.setZipCode(sellerDto.getZipCode() != null ? sellerDto.getZipCode().trim() : null);
        seller.setCountry(sellerDto.getCountry() != null ? sellerDto.getCountry().trim() : null);

        Seller updatedSeller = sellerRepository.save(seller);
        return convertToDto(updatedSeller);
    }

    @Transactional
    public void deleteSeller(Long id) {
        if (!sellerRepository.existsById(id)) {
            throw new SellerNotFoundException("Seller not found with id: " + id);
        }
        sellerRepository.deleteById(id);
    }

    public long countSellers() {
        return sellerRepository.count();
    }

    public List<SellerDto> searchSellers(String searchTerm) {
        List<Seller> sellers = sellerRepository.searchSellers(searchTerm);
        return sellers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<SellerDto> getSellersByName(String sellerName) {
        List<Seller> sellers = sellerRepository.findBySellerNameContainingIgnoreCase(sellerName);
        return sellers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<Seller> getSellerByExactName(String sellerName) {
        List<Seller> sellers = sellerRepository.findBySellerNameContainingIgnoreCase(sellerName);
        return sellers.stream()
                .filter(seller -> seller.getSellerName().equalsIgnoreCase(sellerName.trim()))
                .findFirst();
    }

    public List<SellerDto> getSellersByContactName(String contactName) {
        List<Seller> sellers = sellerRepository.findByContactNameContainingIgnoreCase(contactName);
        return sellers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public SellerDto getSellerByEmail(String email) {
        Seller seller = sellerRepository.findByEmail(email)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with email: " + email));
        return convertToDto(seller);
    }

    public List<SellerDto> getSellersByCity(String city) {
        List<Seller> sellers = sellerRepository.findByCityIgnoreCase(city);
        return sellers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<SellerDto> getSellersByState(String state) {
        List<Seller> sellers = sellerRepository.findByStateIgnoreCase(state);
        return sellers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<SellerDto> getSellersByCountry(String country) {
        List<Seller> sellers = sellerRepository.findByCountryIgnoreCase(country);
        return sellers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public boolean isEmailAvailable(String email) {
        return !sellerRepository.existsByEmail(email);
    }

    private SellerDto convertToDto(Seller seller) {
        SellerDto dto = new SellerDto();
        dto.setId(seller.getId());
        dto.setSellerName(seller.getSellerName());
        dto.setContactName(seller.getContactName());
        dto.setEmail(seller.getEmail());
        dto.setPhoneNumber(seller.getPhoneNumber());
        dto.setAddressLine1(seller.getAddressLine1());
        dto.setAddressLine2(seller.getAddressLine2());
        dto.setCity(seller.getCity());
        dto.setState(seller.getState());
        dto.setZipCode(seller.getZipCode());
        dto.setCountry(seller.getCountry());
        
        // Set combined address for backward compatibility
        dto.setAddress(combineAddress(seller));
        
        return dto;
    }

    private Seller convertToEntity(SellerDto sellerDto) {
        Seller seller = new Seller();
        
        // Handle required fields with proper null checks
        seller.setSellerName(sellerDto.getSellerName() != null ? sellerDto.getSellerName().trim() : "");
        seller.setContactName(sellerDto.getContactName() != null ? sellerDto.getContactName().trim() : "");
        
        // Handle optional fields
        seller.setEmail(sellerDto.getEmail() != null ? sellerDto.getEmail().trim() : null);
        seller.setPhoneNumber(sellerDto.getPhoneNumber() != null ? sellerDto.getPhoneNumber().trim() : null);
        
        // Handle address fields - use individual fields if available, otherwise use combined address
        seller.setAddressLine1(sellerDto.getAddressLine1() != null ? sellerDto.getAddressLine1().trim() : 
                              (sellerDto.getAddress() != null ? sellerDto.getAddress().trim() : null));
        seller.setAddressLine2(sellerDto.getAddressLine2() != null ? sellerDto.getAddressLine2().trim() : null);
        seller.setCity(sellerDto.getCity() != null ? sellerDto.getCity().trim() : null);
        seller.setState(sellerDto.getState() != null ? sellerDto.getState().trim() : null);
        seller.setZipCode(sellerDto.getZipCode() != null ? sellerDto.getZipCode().trim() : null);
        seller.setCountry(sellerDto.getCountry() != null ? sellerDto.getCountry().trim() : null);
        
        return seller;
    }

    private String combineAddress(Seller seller) {
        StringBuilder address = new StringBuilder();
        if (seller.getAddressLine1() != null && !seller.getAddressLine1().isEmpty()) {
            address.append(seller.getAddressLine1());
        }
        if (seller.getAddressLine2() != null && !seller.getAddressLine2().isEmpty()) {
            if (address.length() > 0) address.append(", ");
            address.append(seller.getAddressLine2());
        }
        if (seller.getCity() != null && !seller.getCity().isEmpty()) {
            if (address.length() > 0) address.append(", ");
            address.append(seller.getCity());
        }
        if (seller.getState() != null && !seller.getState().isEmpty()) {
            if (address.length() > 0) address.append(", ");
            address.append(seller.getState());
        }
        if (seller.getZipCode() != null && !seller.getZipCode().isEmpty()) {
            if (address.length() > 0) address.append(" ");
            address.append(seller.getZipCode());
        }
        if (seller.getCountry() != null && !seller.getCountry().isEmpty()) {
            if (address.length() > 0) address.append(", ");
            address.append(seller.getCountry());
        }
        return address.toString();
    }
}
