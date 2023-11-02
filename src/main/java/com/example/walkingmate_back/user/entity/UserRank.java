package com.example.walkingmate_back.user.entity;

import com.example.walkingmate_back.history.dto.BuyHistoryRequestDTO;
import com.example.walkingmate_back.history.dto.CoinRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "userRank")
@Slf4j
public class UserRank {

    @Id
    @Column(name = "userId")
    private String userId;  // 사용자 id

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // 외래키 설정
    private UserEntity user;  // 사용자 id

    @Column(length = 20)
    private String tier;  // 사용자 티어

    @Column
    private int coin;  // 사용자 코인

    @Column
    private int runNum; // 러닝 횟수

    public void update(BuyHistoryRequestDTO buyHistoryRequestDTO) {
        this.coin=coin + buyHistoryRequestDTO.getCoin();
    }

    public void update(CoinRequestDTO coinRequestDTO) {this.coin = coin - coinRequestDTO.getCoin();}

    public void update(int coin) {
        this.coin = this.coin + coin;
    }

    public void update(String tier) { this.tier = tier; }

    public void updateRunNum() {
        log.info("문제점 2");
        this.runNum = runNum + 1;
    }
}
