package com.example.walkingmate_back.history.entity;

import com.example.walkingmate_back.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@IdClass(BuyHistoryId.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "buyHistory")
public class BuyHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 구매 번호 (자동 증가)

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // 외래키 설정
    private UserEntity user;  // 사용자 id

    @Id
    @Column
    private Date date;  // 구매 날짜

    @Column
    private int money;  // 구매 금액

    @Column
    private int coin;  // 구매 코인

}
