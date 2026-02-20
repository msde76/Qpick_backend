package Qpick.server.domain.Order.application;

import Qpick.server.domain.Order.converter.OrderConverter;
import Qpick.server.domain.Order.domain.entity.Order;
import Qpick.server.domain.Order.domain.repository.OrderRepository;
import Qpick.server.domain.Order.dto.OrderRequestDTO;
import Qpick.server.domain.Order.dto.OrderResponseDTO;
import Qpick.server.domain.Order.exception.orderException;
import Qpick.server.domain.product.domain.entity.Product;
import Qpick.server.domain.product.domain.repository.ProductRepository;
import Qpick.server.domain.user.domain.entity.User;
import Qpick.server.global.error.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public OrderResponseDTO.ProductOrderDTO productOrder(User user, OrderRequestDTO.ProductOrderDTO request) {

        // 1. 상품 조회
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new orderException(ErrorStatus.PRODUCT_NOT_FOUND));

        // 2. 재고 감소
        product.decreaseStock(request.getQuantity());

        // 3. 주문 생성 (감소된 후 주문 진행)
        Order newOrder = OrderConverter.toOrder(user, product, request);

        // 4. 주문 저장
        orderRepository.save(newOrder);

        // 5. 응답 반환
        return OrderConverter.toProductOrderDTO(newOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO.ProductOrderInfoDTO getProductOrder(User user, Long orderId) {

        // 1. 주문 조회 (없으면 예외 발생)
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new orderException(ErrorStatus.ORDER_NOT_FOUND)); // 에러코드 확인 필요

        // 2. 내 주문이 맞는지 검증
        if (!order.getUser().getId().equals(user.getId())) {
            throw new orderException(ErrorStatus._FORBIDDEN);
        }

        // 3. DTO 변환 및 반환
        return OrderConverter.toProductOrderInfoDTO(order);
    }

    @Override
    public OrderResponseDTO.OrdersDTO getOrders(User user, Integer page) {

        // 1. 페이징 설정: (조회할 페이지 번호, 한 페이지당 데이터 개수)
        PageRequest pageRequest = PageRequest.of(page, 10); // 한 페이지당 10개씩 가져오기

        // 2. Repository 조회 (Page 객체 반환)
        Page<Order> orderPage = orderRepository.findAllByUserOrderByCreatedAtDesc(user, pageRequest);

        // 3. Converter를 통해 DTO 반환
        return OrderConverter.toOrdersDTO(orderPage);
    }
}
