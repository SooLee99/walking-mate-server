package com.example.walkingmate_back.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BoardCommentRequestDTO {
    private Long id; // 댓글 번호 (자동 증가)

    private Long boardId;  // 게시판 번호

    @NotBlank(message = "내용은 필수 항목입니다.")
    private String content;  // 내용

    public BoardCommentRequestDTO(Long boardId, String content) {
        this.boardId=boardId;
        this.content=content;
    }
}
