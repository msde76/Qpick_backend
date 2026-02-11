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

    public static OrderResponseDTO.ProductOrderInfoDTO toProductOrderInfoDTO(Order order) {
        return OrderResponseDTO.ProductOrderInfoDTO.builder()
                // 주문 정보 매핑
                .orderId(order.getId())
                .orderedAt(order.getCreatedAt())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus().name()) // Enum -> String 변환

                // 상품 정보 매핑
                .productId(order.getProduct().getId())
                .productName(order.getProduct().getName())
                .price(order.getProduct().getPrice())
                .build();
    }
}
