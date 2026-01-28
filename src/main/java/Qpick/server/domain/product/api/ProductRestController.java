package Qpick.server.domain.product.api;

import Qpick.server.domain.product.application.ProductService;
import Qpick.server.domain.product.dto.ProductRequestDTO;
import Qpick.server.domain.product.dto.ProductResponseDTO;
import Qpick.server.global.common.response.BaseResponse;
import Qpick.server.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @PostMapping("")
    @Operation(summary = "상품등록 API", description = "새로운 상품을 등록")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "PRODUCT_200", description = "OK, 성공적으로 등록되었습니다.")
    })
    public BaseResponse<ProductResponseDTO.ProductRegisterDTO> productRegister(
            @RequestBody @Valid ProductRequestDTO.ProductRegisterDTO productRegisterDTO
    ) {
        ProductResponseDTO.ProductRegisterDTO result = productService.productRegister(productRegisterDTO);
        return BaseResponse.onSuccess(SuccessStatus.REGISTER, result);
    }

    @GetMapping("")
    @Operation(summary = "상품 목록 조회 API", description = "등록된 상품들을 최신순으로 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<ProductResponseDTO.ProductListDTO> getProducts(
            @RequestParam(name = "page") Integer page
    ) {
        ProductResponseDTO.ProductListDTO result = productService.getProducts(page);
        return BaseResponse.onSuccess(SuccessStatus.PRODUCT_INFO, result);
    }
}
