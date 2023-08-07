package com.example.walkingmate_back.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BoardCommentRequestDTO {

    private Long parentId;
    
    @NotBlank(message = "내용은 필수 항목입니다.")
    private String content;  // 내용
}
