package com.raphaelcollin.ordermanagement.infrastructure.http;

import com.raphaelcollin.ordermanagement.application.ordermanager.OrderService;
import com.raphaelcollin.ordermanagement.application.ordermanager.request.CreateOrderRequest;
import com.raphaelcollin.ordermanagement.application.ordermanager.response.OrderResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/orders")
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/{id}")
    public OrderResponse getOrder(@PathVariable("id") String orderId) {
        return orderService.getOrder(orderId);
    }

    @PostMapping("/orders")
    public void createOrder(@RequestBody CreateOrderRequest request, HttpServletRequest httpServletRequest) {
        String correlationId = (String) httpServletRequest.getAttribute(CorrelationIdInterceptor.CORRELATION_ID_NAME);
        orderService.createOrder(request.withCorrelationId(correlationId));
    }
}
