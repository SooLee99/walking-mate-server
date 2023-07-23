package com.example.walkingmate_back.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserBodyResponseDTO {

    private String userId;  // 사용자 id

    private int height;  // 사용자 키

    private int weight;  // 사용자 몸무게

    private int BMI;  // BMI

    @Builder
    public UserBodyResponseDTO(String userId, int weight, int height, int BMI) {
        this.userId=userId;
        this.weight=weight;
        this.height=height;
        this.BMI=BMI;
    }
}
