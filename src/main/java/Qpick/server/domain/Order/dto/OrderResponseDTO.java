package Qpick.server.domain.Order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class OrderResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductOrderDTO {
        private Long orderId;
        private Long productId;
        private String productName;
        private Integer quantity;
        private Long totalPrice; // 단가 * 수량
        private LocalDateTime orderedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductOrderInfoDTO {
        // 주문 정보
        private Long orderId;
        private LocalDateTime orderedAt;
        private Integer quantity;
        private Long totalPrice;
        private String status; // 주문 상태 (PENDING, CONFIRMED 등)

        // 상품 정보 (주문한 상품이 무엇인지)
        private Long productId;
        private String productName;
        private Long price; // 상품 1개 가격
    }
}
