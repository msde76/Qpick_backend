package Qpick.server.domain.product.domain.entity;

import Qpick.server.domain.model.entity.BaseTimeEntity;
import Qpick.server.domain.model.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Integer stockQuantity;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    // 낙관적 락(Optimistic Lock) 테스트를 위한 버전 관리
    @Version
    private Long version;

    @Builder
    public Product(String name, Long price, Integer stockQuantity, LocalDateTime startTime, LocalDateTime endTime) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = ProductStatus.READY;
    }

    // 비즈니스 로직: 재고 감소
    public void decreaseStock(int quantity) {
        if (this.stockQuantity - quantity < 0) {
            throw new IllegalArgumentException("재고가 부족합니다."); // 커스텀 Exception으로 변경 추천
        }
        this.stockQuantity -= quantity;
    }
}
