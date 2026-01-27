package Qpick.server.domain.product.converter;

import Qpick.server.domain.product.domain.entity.Product;
import Qpick.server.domain.product.dto.ProductRequestDTO;
import Qpick.server.domain.product.dto.ProductResponseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductConverter {

    public static Product toEntity(ProductRequestDTO.ProductRegisterDTO request) {
        return Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();
    }

    public static ProductResponseDTO.ProductRegisterDTO toRegisterDTO(Product product) {
        return ProductResponseDTO.ProductRegisterDTO.builder()
                .productId(product.getId())
                .name(product.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
