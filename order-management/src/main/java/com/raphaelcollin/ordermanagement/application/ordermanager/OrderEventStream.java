package com.raphaelcollin.ordermanagement.application.ordermanager;

import com.raphaelcollin.ordermanagement.domain.event.OrderEvent;

public interface OrderEventStream {
    void publishEvent(OrderEvent event);
}
