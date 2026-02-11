package Qpick.server.domain.product.converter;

import Qpick.server.domain.product.domain.entity.Product;
import Qpick.server.domain.product.dto.ProductRequestDTO;
import Qpick.server.domain.product.dto.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConverter {

    public static Product toEntity(ProductRequestDTO.ProductRegisterDTO request) {
        return Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .description(request.getDescription())
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

    public static ProductResponseDTO.ProductPreviewDTO toProductPreviewDTO(Product product) {
        return ProductResponseDTO.ProductPreviewDTO.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .createdAt(product.getCreatedAt()) // Auditing 적용 가정
                .build();
    }

    public static ProductResponseDTO.ProductListDTO toProductListDTO(Page<Product> productPage) {

        // 1. Page 안에 있는 내용물을 DTO 리스트로 변환
        List<ProductResponseDTO.ProductPreviewDTO> productList = productPage.stream()
                .map(ProductConverter::toProductPreviewDTO)
                .collect(Collectors.toList());

        // 2. 페이징 메타데이터와 함께 조립
        return ProductResponseDTO.ProductListDTO.builder()
                .productList(productList)
                .isFirst(productPage.isFirst())
                .isLast(productPage.isLast())
                .totalPage(productPage.getTotalPages())
                .totalElements(productPage.getTotalElements())
                .listSize(productList.size())
                .build();
    }

    public static ProductResponseDTO.ProductInfoDTO toProductInfoDTO(Product product) {
        return ProductResponseDTO.ProductInfoDTO.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .description(product.getDescription())
                .startTime(product.getStartTime())
                .endTime(product.getEndTime())
                .build();
    }

    public static ProductResponseDTO.AIChatDTO toAIChatDTO(Product product, String question, String answer) {
        return ProductResponseDTO.AIChatDTO.builder()
                .productId(product.getId())
                .productName(product.getName())
                .question(question)
                .answer(answer)
                .build();
    }
}
