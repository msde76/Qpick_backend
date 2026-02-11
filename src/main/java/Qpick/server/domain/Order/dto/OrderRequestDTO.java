package Qpick.server.domain.Order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class OrderRequestDTO {

    @Getter
    public static class ProductOrderDTO {

        @NotNull(message = "상품 ID는 필수입니다.")
        private Long productId;

        @Min(value = 1, message = "수량은 최소 1개 이상이어야 합니다.")
        private Integer quantity;
    }
}
