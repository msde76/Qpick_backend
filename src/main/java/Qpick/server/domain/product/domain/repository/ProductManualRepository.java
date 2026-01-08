package Qpick.server.domain.product.domain.repository;

import Qpick.server.domain.product.domain.entity.ProductManual;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductManualRepository extends JpaRepository<ProductManual,Integer> {
}
