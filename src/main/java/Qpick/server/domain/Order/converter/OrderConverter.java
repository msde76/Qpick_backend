package Qpick.server.domain.Order.converter;

import Qpick.server.domain.Order.domain.entity.Order;
import Qpick.server.domain.Order.dto.OrderRequestDTO;
import Qpick.server.domain.Order.dto.OrderResponseDTO;
import Qpick.server.domain.product.domain.entity.Product;
import Qpick.server.domain.user.domain.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public static OrderResponseDTO.OrderPreviewDTO toOrderPreviewDTO(Order order) {
        return OrderResponseDTO.OrderPreviewDTO.builder()
                .orderId(order.getId())
                .productName(order.getProduct().getName()) // N+1 문제 주의 (fetch join 권장)
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus().name())
                .orderedAt(order.getCreatedAt())
                .build();
    }

    public static OrderResponseDTO.OrdersDTO toOrdersDTO(Page<Order> orderPage) {
        List<OrderResponseDTO.OrderPreviewDTO> orderPreviewDTOList = orderPage.stream()
                .map(OrderConverter::toOrderPreviewDTO)
                .collect(Collectors.toList());

        return OrderResponseDTO.OrdersDTO.builder()
                .orderList(orderPreviewDTOList)
                .listSize(orderPreviewDTOList.size())
                .totalPage(orderPage.getTotalPages())       // 전체 페이지 수
                .totalElements(orderPage.getTotalElements()) // 전체 데이터 수
                .isFirst(orderPage.isFirst())               // 첫 페이지 여부
                .isLast(orderPage.isLast())                 // 마지막 페이지 여부
                .build();
    }
}
