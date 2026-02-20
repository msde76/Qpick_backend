package Qpick.server.domain.Order.api;

import Qpick.server.domain.Order.application.OrderService;
import Qpick.server.domain.Order.dto.OrderRequestDTO;
import Qpick.server.domain.Order.dto.OrderResponseDTO;
import Qpick.server.domain.user.domain.entity.User;
import Qpick.server.global.annotation.AuthUser;
import Qpick.server.global.common.response.BaseResponse;
import Qpick.server.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;

    @PostMapping("")
    @Operation(summary = "상품 주문(예약) API", description = "특정 상품을 주문합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공적으로 상품이 주문되었습니다.")
    })
    public BaseResponse<OrderResponseDTO.ProductOrderDTO> productOrder(
            @Parameter(hidden = true) @AuthUser User user,
            @RequestBody @Valid OrderRequestDTO.ProductOrderDTO request
    ) {
        OrderResponseDTO.ProductOrderDTO result = orderService.productOrder(user, request);
        return BaseResponse.onSuccess(SuccessStatus.ORDER, result);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "상품 주문 단건 조회 API", description = "특정 상품 주문을 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공적으로 주문이 조회되었습니다.")
    })
    public BaseResponse<OrderResponseDTO.ProductOrderInfoDTO> getProductOrder(
            @Parameter(hidden = true) @AuthUser User user,
            @PathVariable(name = "orderId") Long orderId
    ) {
        OrderResponseDTO.ProductOrderInfoDTO result = orderService.getProductOrder(user, orderId);
        return BaseResponse.onSuccess(SuccessStatus.ORDER_INFO, result);
    }

    @GetMapping("")
    @Operation(summary = "내 주문 내역 조회 API", description = "특정 회원의 주문 목록을 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공적으로 주문이 조회되었습니다.")
    })
    public BaseResponse<OrderResponseDTO.OrdersDTO> getOrders(
            @Parameter(hidden = true) @AuthUser User user,
            @RequestParam(name = "page", defaultValue = "0") Integer page
    ) {
        OrderResponseDTO.OrdersDTO result = orderService.getOrders(user, page);
        return BaseResponse.onSuccess(SuccessStatus.ORDERS, result);
    }
}
