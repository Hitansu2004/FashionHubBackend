package com.nisum.inventoryService.dto;

public class ReserveRequest {
    private Integer orderId;
    private String sku;
    private Integer reservedQty;

    public ReserveRequest() {}

    public ReserveRequest(Integer orderId, String sku,  Integer reservedQty) {
        this.orderId = orderId;
        this.sku = sku;
        this.reservedQty = reservedQty;
    }

    public Integer getOrderId() { return orderId; }

    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public String getSku() { return sku; }

    public void setSku(String sku) { this.sku = sku; }


    public Integer getReservedQty() { return reservedQty; }

    public void setReservedQty(Integer reservedQty) { this.reservedQty = reservedQty; }
}