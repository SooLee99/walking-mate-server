package com.example.walkingmate_back.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BoardCommentRequestDTO {
    private Long id; // 댓글 번호 (자동 증가)

    private Long boardId;  // 게시판 번호

    private String userId;  // 사용자 id

    @NotBlank(message = "내용은 필수 항목입니다.")
    private String content;  // 내용

    public BoardCommentRequestDTO(Long id, Long boardId, String userId, String content) {
        this.id=id;
        this.boardId=boardId;
        this.userId=userId;
        this.content=content;
    }
}
