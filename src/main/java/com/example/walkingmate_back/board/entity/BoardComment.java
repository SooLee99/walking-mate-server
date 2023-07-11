package com.example.walkingmate_back.board.entity;

import com.example.walkingmate_back.board.dto.BoardCommentRequestDTO;
import com.example.walkingmate_back.main.entity.BaseTimeEntity;
import com.example.walkingmate_back.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "boardComment")
public class BoardComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id; // 댓글 번호 (자동 증가)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId") // 외래키 설정
    private Board board;  // 게시판 번호

    // 시간 빼둠

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // 외래키 설정
    private UserEntity user;  // 사용자 id

    @Column
    private String content;  // 내용

    public BoardComment(Board board, UserEntity user, String content) {
        this.board=board;
        this.user=user;
        this.content=content;
    }

    // 댓글 수정
    public BoardComment update(BoardCommentRequestDTO boardCommentRequestDTO) {
        this.content=boardCommentRequestDTO.getContent();
        return this;
    }
}
