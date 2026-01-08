package Qpick.server.domain.Order.domain.repository;

import Qpick.server.domain.Order.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
}
