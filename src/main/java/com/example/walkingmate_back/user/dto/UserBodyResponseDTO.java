package com.example.walkingmate_back.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserBodyResponseDTO {

    private String userId;  // 사용자 id

    private double height;  // 사용자 키

    private double weight;  // 사용자 몸무게

    private double BMI;  // BMI

    @Builder
    public UserBodyResponseDTO(String userId, double weight, double height, double BMI) {
        this.userId=userId;
        this.weight=weight;
        this.height=height;
        this.BMI=BMI;
    }
}
