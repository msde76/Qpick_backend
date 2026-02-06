package Qpick.server.domain.Order.converter;

import Qpick.server.domain.Order.domain.entity.Order;
import Qpick.server.domain.Order.dto.OrderRequestDTO;
import Qpick.server.domain.Order.dto.OrderResponseDTO;
import Qpick.server.domain.product.domain.entity.Product;
import Qpick.server.domain.user.domain.entity.User;

import org.springframework.stereotype.Component;

@Component
public class OrderConverter {

    public static Order toOrder(User user, Product product, OrderRequestDTO.ProductOrderDTO request) {
        return Order.builder()
                .user(user)
                .product(product)
                .quantity(request.getQuantity())
                .totalPrice(product.getPrice() * request.getQuantity())
                .build();
    }

    public static OrderResponseDTO.ProductOrderDTO toProductOrderDTO(Order order) {
        return OrderResponseDTO.ProductOrderDTO.builder()
                .orderId(order.getId())
                .productId(order.getProduct().getId())
                .productName(order.getProduct().getName())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .orderedAt(order.getCreatedAt()) // BaseEntity 상속 가정
                .build();
    }
}
