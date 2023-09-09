package com.example.walkingmate_back.board.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class BoardCommentResponseDTO {

    private Long id; // 댓글 번호 (자동 증가)

    private Long boardId;  // 게시판 번호

    private String userId;  // 사용자 id

    private String content;  // 내용

    private LocalDateTime regTime; // 작성 시간

    private LocalDateTime updateTime; // 수정 시간

    private Long parentId; // 댓글 아이디

    private int recommend; // 좋아요

    private boolean isrecommend;  // 사용자의 좋아요 여부

    private List<BoardCommentResponseDTO> children = new ArrayList<>();

    @Builder
    public BoardCommentResponseDTO(Long id, Long boardId, String userId, String content, Long parentId, LocalDateTime regTime, LocalDateTime updateTime, int recommend, boolean isrecommend, List<BoardCommentResponseDTO> children) {
        this.id=id;
        this.boardId=boardId;
        this.userId=userId;
        this.content=content;
        this.parentId=parentId;
        this.children=children;
        this.regTime=regTime;
        this.updateTime=updateTime;
        this.recommend=recommend;
        this.isrecommend=isrecommend;
    }

    public BoardCommentResponseDTO(Long id, Long boardId, String userId, String content, Long parentId, LocalDateTime regTime, LocalDateTime updateTime) {
        this.id=id;
        this.boardId=boardId;
        this.userId=userId;
        this.content=content;
        this.parentId=parentId;
        this.regTime=regTime;
        this.updateTime=updateTime;
    }

}
