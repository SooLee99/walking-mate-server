package com.example.walkingmate_back.board.dto;

import lombok.Data;

@Data
public class RecommendRequestDTO {

    private String userId;
    private Long boardId;

    public RecommendRequestDTO(String userId, Long boardId) {
        this.userId=userId;
        this.boardId=boardId;
    }
}
