package Qpick.server.domain.Order.domain.repository;

import Qpick.server.domain.Order.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
