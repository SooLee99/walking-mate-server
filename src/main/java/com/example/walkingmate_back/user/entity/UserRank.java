package com.example.walkingmate_back.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "userRank")
public class UserRank {

    @Id
    @Column(name = "userId")
    private String userId;  // 사용자 id

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // 외래키 설정
    private UserEntity user;  // 사용자 id

    @Column
    private String tear;  // 사용자 티어

    @Column
    private int coin;  // 사용자 코인
}
