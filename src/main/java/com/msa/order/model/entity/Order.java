package com.msa.order.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long productId;
    private Long userId;
    private Long orderQty;
    private Long orderUnitPrice;
    private Long orderTotalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Order create(Long userId, Long productId, Long orderQty, Long orderUnitPrice){

        Order order = new Order();

        order.productId = productId;
        order.userId = userId;
        order.orderQty = orderQty;
        order.orderUnitPrice = orderUnitPrice;
        order.orderTotalPrice = orderUnitPrice * orderQty;
        order.createdAt = LocalDateTime.now();
        order.updatedAt = order.createdAt;

        return order;
    }
}
