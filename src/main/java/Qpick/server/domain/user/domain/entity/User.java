package Qpick.server.domain.user.domain.entity;

import Qpick.server.domain.model.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Long point = 0L;

    @Builder
    public User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.point = 0L;
    }

    // 비즈니스 로직: 포인트 차감
    public void usePoint(Long amount) {
        if (this.point < amount) {
            throw new IllegalArgumentException("포인트가 부족합니다.");
        }
        this.point -= amount;
    }
}
