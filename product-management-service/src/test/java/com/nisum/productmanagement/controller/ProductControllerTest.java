package com.nisum.productmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.productmanagement.dto.*;
import com.nisum.productmanagement.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllProducts() throws Exception {
        PaginationResult<ProductDto> result = new PaginationResult<>();
        result.setTotalProductCount(1);
        result.setProducts(Collections.singletonList(new ProductDto()));
        Mockito.when(productService.getAllProducts(Mockito.anyInt(), Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(result);
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("X-Total-Count", "1"));
    }

    @Test
    void testGetProductById() throws Exception {
        ProductDto productDto = new ProductDto();
        Mockito.when(productService.getProductById(1L)).thenReturn(productDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testCreateProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        Mockito.when(productService.createProduct(Mockito.any(ProductDto.class))).thenReturn(productDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testUpdateProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        Mockito.when(productService.updateProduct(Mockito.eq(1L), Mockito.any(ProductDto.class))).thenReturn(productDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteProduct() throws Exception {
        Mockito.when(productService.deleteProduct(1L)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetAllProductIds() throws Exception {
        Mockito.when(productService.getAllProductIds()).thenReturn(Arrays.asList(1L, 2L));
        mockMvc.perform(MockMvcRequestBuilders.get("/products/ids"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetProductSizes() throws Exception {
        Mockito.when(productService.getProductSizes(1L)).thenReturn(Arrays.asList("S", "M"));
        mockMvc.perform(MockMvcRequestBuilders.get("/products/1/sizes"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetProductSkus() throws Exception {
        Mockito.when(productService.getProductSkus(1L, null)).thenReturn(Arrays.asList("SKU1", "SKU2"));
        mockMvc.perform(MockMvcRequestBuilders.get("/products/1/skus"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetProductSeller() throws Exception {
        SellerDto sellerDto = new SellerDto();
        Mockito.when(productService.getProductSellerDetails(1L)).thenReturn(sellerDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/1/sellers"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetAllProductAttributes() throws Exception {
        PaginationResult<ProductAttributeDetailDto> result = new PaginationResult<>();
        result.setTotalProductCount(1);
        result.setProducts(Collections.singletonList(new ProductAttributeDetailDto()));
        Mockito.when(productService.getAllProductAttributes(Mockito.anyInt(), Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(result);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/attributes"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetProductStats() throws Exception {
        Map<String, Object> stats = new HashMap<>();
        stats.put("count", 10);
        Mockito.when(productService.getProductStats()).thenReturn(stats);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/stats"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetProductsByCategory() throws Exception {
        List<ProductDto> products = Arrays.asList(new ProductDto(), new ProductDto());
        Mockito.when(productService.getProductsByCategory(1L)).thenReturn(products);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/category/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetProductByIdAndSize() throws Exception {
        ProductDto productDto = new ProductDto();
        Mockito.when(productService.getProductByIdAndSize(1L, "M")).thenReturn(productDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/1/sizes/M"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testImportProductsSuccess() throws Exception {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        Mockito.doNothing().when(productService).importProducts(Mockito.any(MultipartFile.class));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/products/import")
                .file("file", "test-data".getBytes()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testImportProductsFailure() throws Exception {
        Mockito.doThrow(new RuntimeException("Import failed")).when(productService).importProducts(Mockito.any(MultipartFile.class));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/products/import")
                .file("file", "test-data".getBytes()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testDeleteProductNotFound() throws Exception {
        Mockito.when(productService.deleteProduct(99L)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/99"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetAllProductsWithInvalidPageSize() throws Exception {
        PaginationResult<ProductDto> result = new PaginationResult<>();
        result.setTotalProductCount(0);
        result.setProducts(Collections.emptyList());
        Mockito.when(productService.getAllProducts(Mockito.anyInt(), Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(result);
        mockMvc.perform(MockMvcRequestBuilders.get("/products?page=0&size=200"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("X-Page-Size", "10"));
    }

    @Test
    void testGetAllProductAttributesWithSearch() throws Exception {
        PaginationResult<ProductAttributeDetailDto> result = new PaginationResult<>();
        result.setTotalProductCount(1);
        result.setProducts(Collections.singletonList(new ProductAttributeDetailDto()));
        Mockito.when(productService.getAllProductAttributes(Mockito.anyInt(), Mockito.anyInt(), Mockito.eq("test"), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(result);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/attributes?search=test"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetProductStatsContent() throws Exception {
        Map<String, Object> stats = new HashMap<>();
        stats.put("count", 10);
        stats.put("avgPrice", 100.0);
        Mockito.when(productService.getProductStats()).thenReturn(stats);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/stats"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.avgPrice").value(100.0));
    }

    @Test
    void testGetProductsByCategoryError() throws Exception {
        Mockito.when(productService.getProductsByCategory(999L)).thenThrow(new RuntimeException("DB error"));
        mockMvc.perform(MockMvcRequestBuilders.get("/products/category/999"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void testGetProductSkusWithSize() throws Exception {
        Mockito.when(productService.getProductSkus(1L, "L")).thenReturn(Arrays.asList("SKU-L1", "SKU-L2"));
        mockMvc.perform(MockMvcRequestBuilders.get("/products/1/skus?size=L"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testUpdateProductReturnsUpdated() throws Exception {
        ProductDto productDto = new ProductDto();
        Mockito.when(productService.updateProduct(Mockito.eq(2L), Mockito.any(ProductDto.class))).thenReturn(productDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/products/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testCreateProductReturnsCreated() throws Exception {
        ProductDto productDto = new ProductDto();
        Mockito.when(productService.createProduct(Mockito.any(ProductDto.class))).thenReturn(productDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
