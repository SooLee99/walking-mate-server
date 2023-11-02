package com.example.walkingmate_back.user.entity;

import com.example.walkingmate_back.user.dto.UserBodyUpdateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "userBody")
public class UserBody {

    @Id
    @Column(name = "userId")
    private String userId;  // 사용자 id

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // 외래키 설정
    private UserEntity user;  // 사용자 id

    @Column
    private double height;  // 사용자 키

    @Column
    private double weight;  // 사용자 몸무게

    // DTO를 사용하여 업데이트
    public UserBody update(UserBodyUpdateDTO userBodyUpdateDTO) {
        this.height = userBodyUpdateDTO.getHeight();
        this.weight = userBodyUpdateDTO.getWeight();
        return this;
    }
}
