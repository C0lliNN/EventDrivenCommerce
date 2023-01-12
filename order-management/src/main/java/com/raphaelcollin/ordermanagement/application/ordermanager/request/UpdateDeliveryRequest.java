package com.raphaelcollin.ordermanagement.application.ordermanager.request;

import com.raphaelcollin.ordermanagement.domain.entity.Delivery;
import lombok.Value;

@Value
public class UpdateDeliveryRequest {
    String orderId;
    Delivery.DeliveryStatus deliveryStatus;
}
