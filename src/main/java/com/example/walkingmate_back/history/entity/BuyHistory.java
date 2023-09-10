package com.example.walkingmate_back.history.entity;

import com.example.walkingmate_back.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "buyHistory")
public class BuyHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id; // 구매 번호 (자동 증가)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // 외래키 설정
    private UserEntity user;  // 사용자 id

    @Column
    private LocalDateTime date;  // 구매 날짜

    @Column
    private int money;  // 구매 금액

    @Column
    private int coin;  // 구매 코인

    public BuyHistory(UserEntity user, LocalDateTime date, int coin, int money) {
        this.user=user;
        this.date=date;
        this.coin=coin;
        this.money=money;
    }
}
