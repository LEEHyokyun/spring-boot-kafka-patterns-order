package com.msa.order.service;

import com.msa.order.infra.kafka.event.Event;
import com.msa.order.infra.kafka.event.EventType;
import com.msa.order.infra.kafka.event.payload.OrderCreatedEventPayload;
import com.msa.order.infra.kafka.event.eventpublisher.KafkaEventPublisher;
import com.msa.order.model.entity.Order;
import com.msa.order.model.request.OrderCreateRequest;
import com.msa.order.model.response.OrderResponse;
import com.msa.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaEventPublisher kafkaEventPublisher;

    @Transactional
    public OrderResponse create(Long userId, OrderCreateRequest orderCreateRequest) {
        Order order = Order.create(
                userId,
                orderCreateRequest.getProductId(),
                orderCreateRequest.getOrderQty(),
                orderCreateRequest.getOrderUnitPrice()
        );

        orderRepository.save(order);

        kafkaEventPublisher.publish(
                Event.builder()
                        .eventTopic(EventType.Topic.CLOUD_NATIVE_MSA_ORDER)
                        .eventType(EventType.ORDER_CREATED)
                        .payload(
                                OrderCreatedEventPayload.builder()
                                        .productId(orderCreateRequest.getProductId())
                                        .orderedQty(orderCreateRequest.getOrderQty())
                                        .build()
                        )
                        .build()
        );

        return OrderResponse.from(order);
    }

    public List<OrderResponse> readOrderOfUser(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(OrderResponse::from)
                .toList();
    }

    public List<OrderResponse> readAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderResponse::from)//Entity > Dto
                .toList();
    }

}
