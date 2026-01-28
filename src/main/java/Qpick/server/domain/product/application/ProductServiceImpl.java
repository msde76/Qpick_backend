package Qpick.server.domain.product.application;

import Qpick.server.domain.product.converter.ProductConverter;
import Qpick.server.domain.product.domain.entity.Product;
import Qpick.server.domain.product.domain.repository.ProductRepository;
import Qpick.server.domain.product.dto.ProductRequestDTO;
import Qpick.server.domain.product.dto.ProductResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO.ProductListDTO getProducts(Integer page) {

        // 1. PageRequest 생성
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 2. Repository 호출
        Page<Product> productPage = productRepository.findAll(pageRequest);

        // 3. 변환해서 반환
        return ProductConverter.toProductListDTO(productPage);
    }
}
