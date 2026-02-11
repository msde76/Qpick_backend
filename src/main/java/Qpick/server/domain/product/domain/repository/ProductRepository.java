package Qpick.server.domain.product.domain.repository;

import Qpick.server.domain.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
