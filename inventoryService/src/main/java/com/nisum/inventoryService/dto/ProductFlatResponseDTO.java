package com.nisum.inventoryService.dto;

import java.util.List;

public class ProductFlatResponseDTO {
    private List<ProductFlatItemDTO> products; // renamed for clarity
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private int totalResults;

    // Inner DTO representing flat product fields
    public static class ProductFlatItemDTO {
        private String status;
        private String sku;
        private int categoryId;

        public ProductFlatItemDTO(String status, String sku, int categoryId) {
            this.status = status;
            this.sku = sku;
            this.categoryId = categoryId;
        }

        // Getters and Setters
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }
    }

    // Getters and Setters for response metadata and product list
    public List<ProductFlatItemDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductFlatItemDTO> products) {
        this.products = products;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
