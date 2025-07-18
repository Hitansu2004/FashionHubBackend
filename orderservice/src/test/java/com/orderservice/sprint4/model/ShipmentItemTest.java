package com.orderservice.sprint4.model;

import com.orderservice.sprint4.model.enmus.ShipmentItemStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ShipmentItemTest {
    @Test
    void testShipmentItemFields() {
        ShipmentItem item = new ShipmentItem();
        item.setItemTrackingId("TRACK123");
        item.setShipmentDate(LocalDateTime.now());
        item.setItemStatus(ShipmentItemStatus.InTransit);

        Assertions.assertEquals("TRACK123", item.getItemTrackingId());
        Assertions.assertEquals(ShipmentItemStatus.InTransit, item.getItemStatus());
    }
}
