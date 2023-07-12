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
@Table(name = "userBody")
public class UserBody {

    @Id
    @Column(name = "userId")
    private String userId;  // 사용자 id

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // 외래키 설정
    private UserEntity user;  // 사용자 id

    @Column
    private int height;  // 사용자 키

    @Column
    private int weight;  // 사용자 몸무게

}
