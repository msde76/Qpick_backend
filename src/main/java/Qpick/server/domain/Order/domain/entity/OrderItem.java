package Qpick.server.domain.Order.domain.entity;

import Qpick.server.domain.model.entity.BaseTimeEntity;
import Qpick.server.domain.product.domain.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "order_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer count;
    private Long price; // 구매 당시 가격

    @Builder
    public OrderItem(Order order, Product product, Integer count, Long price) {
        this.order = order;
        this.product = product;
        this.count = count;
        this.price = price;
    }
}
