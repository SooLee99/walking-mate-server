package com.example.walkingmate_back.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardResponseDTO {

    private Long id; // 게시판 번호 (자동 증가)

    private String userId;  // 사용자 id
    
    private String title;  // 제목
    
    private String content;  // 내용


    public BoardResponseDTO(Long id, String userId, String title, String content, LocalDateTime regTime, LocalDateTime updateTime) {
        this.id=id;
        this.userId=userId;
        this.title=title;
        this.content=content;
    }
}
