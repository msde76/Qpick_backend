package Qpick.server.domain.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

public class ProductRequestDTO {

    @Getter
    public static class ProductRegisterDTO {
        @NotBlank(message = "상품 이름은 필수입니다.")
        private String name;

        @NotNull(message = "가격은 필수입니다.")
        @Min(value = 100, message = "가격은 최소 100원 이상이어야 합니다.")
        private Long price;

        @NotNull(message = "재고 수량은 필수입니다.")
        @Min(value = 1, message = "재고는 최소 1개 이상이어야 합니다.")
        private Integer stockQuantity;

        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }
}
