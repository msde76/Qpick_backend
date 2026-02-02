package Qpick.server.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ProductResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductRegisterDTO {
        private Long productId;
        private String name;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductListDTO {
        private List<ProductPreviewDTO> productList; // 실제 상품 리스트
        private Integer listSize;      // 현재 페이지의 아이템 개수
        private Integer totalPage;     // 전체 페이지 수
        private Long totalElements;    // 전체 아이템 개수
        private Boolean isFirst;       // 첫 페이지 여부
        private Boolean isLast;        // 마지막 페이지 여부
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductPreviewDTO {
        private Long productId;
        private String name;
        private Long price;
        private Integer stockQuantity;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductInfoDTO {
        private Long productId;
        private String name;
        private Long price;
        private Integer stockQuantity;
        private String description;
        // private String imageUrl; // 나중에 이미지 생기면 추가
        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AIChatDTO {
        private Long productId;
        private String productName;
        private String question;
        private String answer;
    }
}
