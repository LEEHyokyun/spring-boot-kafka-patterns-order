package com.msa.order.model.response;

import com.msa.order.model.entity.Order;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class OrderResponse {
    private Long orderId;
    private Long productId;
    private Long userId;
    private Long orderQty;
    private Long orderUnitPrice;
    private Long orderTotalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderResponse from(Order order) {
        OrderResponse orderResponse = new OrderResponse();

        orderResponse.orderId = order.getOrderId();
        orderResponse.productId = order.getProductId();
        orderResponse.userId = order.getUserId();
        orderResponse.orderQty = order.getOrderQty();
        orderResponse.orderUnitPrice = order.getOrderUnitPrice();
        orderResponse.orderTotalPrice = order.getOrderTotalPrice();
        orderResponse.createdAt = order.getCreatedAt();
        orderResponse.updatedAt = order.getUpdatedAt();

        return orderResponse;
    }
}
