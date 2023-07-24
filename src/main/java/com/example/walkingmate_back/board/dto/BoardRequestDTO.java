package com.example.walkingmate_back.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BoardRequestDTO {

    private Long id; // 게시판 번호 (자동 증가)


    @NotBlank(message = "제목은 필수 항목입니다.")
    private String title;  // 제목

    @NotBlank(message = "내용은 필수 항목입니다.")
    private String content;  // 내용

    public BoardRequestDTO(String title, String content) {
        this.title=title;
        this.content=content;
    }
}
