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
}
