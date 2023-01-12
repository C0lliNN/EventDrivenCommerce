package com.raphaelcollin.shippingmanagement.application.request;

import com.raphaelcollin.shippingmanagement.domain.entity.ShippingStatus;
import lombok.Value;

@Value
public class UpdateShippingRequest {
    String upstreamExternalIdentifier;
    ShippingStatus status;
}
