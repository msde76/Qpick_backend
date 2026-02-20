package Qpick.server.domain.Order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderPreviewDTO {
        private Long orderId;
        private String productName;
        private Integer quantity;
        private Long totalPrice;
        private String status;
        private LocalDateTime orderedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrdersDTO {
        private List<OrderPreviewDTO> orderList;
        private Integer listSize;      // 현재 페이지의 데이터 개수
        private Integer totalPage;     // 전체 페이지 수
        private Long totalElements;    // 전체 데이터 총 개수
        private Boolean isFirst;       // 첫 번째 페이지인지 여부
        private Boolean isLast;        // 마지막 페이지인지 여부
    }
}
