package com.example.walkingmate_back.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardCommentResponseDTO {

    private Long id; // 댓글 번호 (자동 증가)

    private Long boardId;  // 게시판 번호

    private String userId;  // 사용자 id

    private String content;  // 내용

    public BoardCommentResponseDTO(Long id, Long boardId, String userId, String content, LocalDateTime regTime, LocalDateTime updateTime) {
        this.id=id;
        this.boardId=boardId;
        this.userId=userId;
        this.content=content;
    }

}
