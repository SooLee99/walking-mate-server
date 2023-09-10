package com.example.walkingmate_back.board.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class BoardResponseDTO {

    private Long id; // 게시판 번호 (자동 증가)

    private String userId;  // 사용자 id
    
    private String title;  // 제목
    
    private String content;  // 내용

    private LocalDateTime regTime; // 작성 시간

    private LocalDateTime updateTime; // 수정 시간

    private int recommend; // 좋아요

    private boolean isrecommend;  // 사용자의 좋아요 여부

    List<BoardCommentResponseDTO> comments = new ArrayList<>(); // 댓글 리스트

    @Builder
    public BoardResponseDTO(Long id, String userId, String title, String content, LocalDateTime regTime, LocalDateTime updateTime, int recommend, boolean isrecommend, List<BoardCommentResponseDTO> comments) {
        this.id=id;
        this.userId=userId;
        this.title=title;
        this.content=content;
        this.comments=comments;
        this.regTime=regTime;
        this.updateTime=updateTime;
        this.recommend=recommend;
        this.isrecommend=isrecommend;
    }

}
