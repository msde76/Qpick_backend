package Qpick.server.domain.Order.application;

import Qpick.server.domain.Order.dto.OrderRequestDTO;
import Qpick.server.domain.Order.dto.OrderResponseDTO;
import Qpick.server.domain.user.domain.entity.User;

public interface OrderService {

    OrderResponseDTO.ProductOrderDTO productOrder(User user, OrderRequestDTO.ProductOrderDTO request);
}
