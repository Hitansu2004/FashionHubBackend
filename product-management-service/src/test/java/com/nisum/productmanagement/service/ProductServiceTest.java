package com.nisum.productmanagement.service;

import com.nisum.productmanagement.dto.*;
import com.nisum.productmanagement.exception.ProductNotFoundException;
import com.nisum.productmanagement.exception.CategoryNotFoundException;
import com.nisum.productmanagement.model.Product;
import com.nisum.productmanagement.model.ProductAttribute;
import com.nisum.productmanagement.model.Category;
import com.nisum.productmanagement.model.Seller;
import com.nisum.productmanagement.repository.ProductRepository;
import com.nisum.productmanagement.repository.CategoryRepository;
import com.nisum.productmanagement.repository.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private SellerRepository sellerRepository;
    @Mock
    private CategoryService categoryService;
    @Mock
    private CsvParserService csvParserService;
    @Mock
    private SellerService sellerService;
    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTotalProductCount() {
        Mockito.when(productRepository.count()).thenReturn(10L);
        assertEquals(10, productService.getTotalProductCount());
    }

    @Test
    void testGetAllProducts() {
        Product product = new Product();
        product.setId(1L);
        Mockito.when(productRepository.findAll()).thenReturn(List.of(product));
        List<ProductDto> result = productService.getAllProducts();
        assertEquals(1, result.size());
    }

    @Test
    void testGetProductByIdFound() {
        Product product = new Product();
        product.setId(1L);
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        ProductDto dto = productService.getProductById(1L);
        assertEquals(1L, dto.getId());
    }

    @Test
    void testGetProductByIdNotFound() {
        Mockito.when(productRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(2L));
    }

    @Test
    void testCreateProductValid() {
        ProductDto dto = new ProductDto();
        dto.setName("Test");
        dto.setCategoryId(1L);
        dto.setSellerId(2L);
        Product product = new Product();
        product.setId(1L);
        product.setName("Test");
        product.setCategoryId(1L); // Ensure categoryId is set
        product.setSellerId(2L);
        Mockito.when(categoryRepository.findById(1)).thenReturn(Optional.of(new Category()));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        ProductDto result = productService.createProduct(dto);
        assertEquals("Test", result.getName());
    }

    @Test
    void testCreateProductInvalidName() {
        ProductDto dto = new ProductDto();
        dto.setCategoryId(1L);
        dto.setSellerId(2L);
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(dto));
    }

    @Test
    void testCreateProductInvalidCategory() {
        ProductDto dto = new ProductDto();
        dto.setName("Test");
        dto.setSellerId(2L);
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(dto));
    }

    @Test
    void testCreateProductInvalidSeller() {
        ProductDto dto = new ProductDto();
        dto.setName("Test");
        dto.setCategoryId(1L);
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(dto));
    }

    @Test
    void testUpdateProductFound() {
        ProductDto dto = new ProductDto();
        dto.setName("Updated");
        dto.setCategoryId(1L);
        dto.setSellerId(2L);
        Product product = new Product();
        product.setId(1L);
        product.setCategoryId(1L);
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        Mockito.when(categoryRepository.findById(1)).thenReturn(Optional.of(new Category()));
        ProductDto result = productService.updateProduct(1L, dto);
        assertEquals("Updated", result.getName());
    }

    @Test
    void testUpdateProductNotFound() {
        ProductDto dto = new ProductDto();
        dto.setName("Updated");
        dto.setCategoryId(1L);
        dto.setSellerId(2L);
        Mockito.when(productRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(2L, dto));
    }

    @Test
    void testDeleteProductFound() {
        Mockito.when(productRepository.existsById(1L)).thenReturn(true);
        assertTrue(productService.deleteProduct(1L));
    }

    @Test
    void testDeleteProductNotFound() {
        Mockito.when(productRepository.existsById(2L)).thenReturn(false);
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(2L));
    }

    @Test
    void testImportProductsEmptyFile() {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        Mockito.when(file.isEmpty()).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> productService.importProducts(file));
    }

    @Test
    void testImportProductsInvalidFormat() {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        Mockito.when(file.isEmpty()).thenReturn(false);
        Mockito.when(file.getOriginalFilename()).thenReturn("file.pdf");
        assertThrows(IllegalArgumentException.class, () -> productService.importProducts(file));
    }

    @Test
    void testGetAllProductIds() {
        Product product = new Product();
        product.setId(1L);
        Mockito.when(productRepository.findAll()).thenReturn(List.of(product));
        List<Long> ids = productService.getAllProductIds();
        assertEquals(1, ids.size());
    }

    @Test
    void testGetProductSizesFound() {
        Product product = new Product();
        product.setId(1L);
        ProductAttribute attr = new ProductAttribute();
        attr.setSize("M");
        product.setAttributes(List.of(attr));
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        List<String> sizes = productService.getProductSizes(1L);
        assertEquals(List.of("M"), sizes);
    }

    @Test
    void testGetProductSizesNotFound() {
        Product product = new Product();
        product.setId(1L);
        product.setAttributes(new ArrayList<>());
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        assertThrows(ProductNotFoundException.class, () -> productService.getProductSizes(1L));
    }

    @Test
    void testGetProductSellersFound() {
        Product product = new Product();
        product.setId(1L);
        product.setSellerId(2L);
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        List<Long> sellers = productService.getProductSellers(1L);
        assertEquals(List.of(2L), sellers);
    }

    @Test
    void testGetProductSellerDetailsNotFound() {
        Product product = new Product();
        product.setId(1L);
        product.setSellerId(2L);
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(sellerRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> productService.getProductSellerDetails(1L));
    }

    @Test
    void testGetProductSkusFound() {
        Product product = new Product();
        product.setId(1L);
        ProductAttribute attr = new ProductAttribute();
        attr.setSku("SKU1");
        attr.setSize("L");
        product.setAttributes(List.of(attr));
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        List<String> skus = productService.getProductSkus(1L, "L");
        assertEquals(List.of("SKU1"), skus);
    }

    @Test
    void testGetProductSkusNotFound() {
        Product product = new Product();
        product.setId(1L);
        product.setAttributes(new ArrayList<>());
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        List<String> skus = productService.getProductSkus(1L, null);
        assertEquals(List.of(), skus);
    }
}
