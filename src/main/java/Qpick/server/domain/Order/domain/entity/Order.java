package Qpick.server.domain.Order.domain.entity;

import Qpick.server.domain.model.entity.BaseTimeEntity;
import Qpick.server.domain.model.enums.OrderStatus;
import Qpick.server.domain.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Long totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Builder
    public Order(User user, Long totalPrice) {
        this.user = user;
        this.totalPrice = totalPrice;
        this.status = OrderStatus.PENDING;
    }

    public void complete() {
        this.status = OrderStatus.CONFIRMED;
    }

    public void cancel() {
        this.status = OrderStatus.CANCELED;
    }
}
