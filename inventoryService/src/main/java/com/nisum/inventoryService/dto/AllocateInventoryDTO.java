package com.nisum.inventoryService.dto;

public class AllocateInventoryDTO {
    private Integer orderId;
    private Integer isCancelled; // 0 or 1

    public AllocateInventoryDTO() {}

    public AllocateInventoryDTO(Integer orderId, Integer isCancelled) {
        this.orderId = orderId;
        this.isCancelled = isCancelled;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Integer isCancelled) {
        this.isCancelled = isCancelled;
    }
}
