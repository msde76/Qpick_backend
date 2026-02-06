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
import Qpick.server.domain.user.domain.repository.UserRepository;
import Qpick.server.global.error.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
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
}
