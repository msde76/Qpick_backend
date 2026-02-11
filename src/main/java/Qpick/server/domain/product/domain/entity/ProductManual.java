package Qpick.server.domain.product.domain.entity;

import Qpick.server.domain.model.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "product_manuals")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductManual extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manual_id")
    private Long id;

    // FetchType.LAZY 필수: 상품 조회 시 불필요한 로딩 방지
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Lob // 대용량 텍스트
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private boolean isEmbedded = false; // Redis 벡터화 여부

    public void updateEmbeddedStatus(boolean status) {
        this.isEmbedded = status;
    }
}
