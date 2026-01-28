package Qpick.server.domain.product.application;

import Qpick.server.domain.product.dto.ProductRequestDTO;
import Qpick.server.domain.product.dto.ProductResponseDTO;

public interface ProductService {

    ProductResponseDTO.ProductRegisterDTO productRegister(ProductRequestDTO.ProductRegisterDTO request);

    ProductResponseDTO.ProductListDTO getProducts(Integer page);
}
