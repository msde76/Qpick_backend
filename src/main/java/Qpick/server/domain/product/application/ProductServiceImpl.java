package Qpick.server.domain.product.application;

import Qpick.server.domain.product.converter.ProductConverter;
import Qpick.server.domain.product.domain.entity.Product;
import Qpick.server.domain.product.domain.repository.ProductRepository;
import Qpick.server.domain.product.dto.ProductRequestDTO;
import Qpick.server.domain.product.dto.ProductResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductResponseDTO.ProductRegisterDTO productRegister(ProductRequestDTO.ProductRegisterDTO request) {

        // 1. Entity 변환
        Product newProduct = ProductConverter.toEntity(request);

        // 2. DB 저장
        Product savedProduct = productRepository.save(newProduct);

        // 3. 응답 DTO 반환
        return ProductConverter.toRegisterDTO(savedProduct);
    }
}
