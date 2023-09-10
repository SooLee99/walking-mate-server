package com.example.walkingmate_back.board.entity;

import com.example.walkingmate_back.board.dto.BoardCommentUpdateDTO;
import com.example.walkingmate_back.main.entity.BaseTimeEntity;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id; // 댓글 번호 (자동 증가)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId") // 외래키 설정
    private Board board;  // 게시판 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private BoardComment parent;

    // 시간 빼둠

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // 외래키 설정
    private UserEntity user;  // 사용자 id

    @Column(length = 500)
    private String content;  // 내용

    @Column(columnDefinition = "int default 0")
    private int recommend; // 댓글 좋아요

    @JsonIgnore
    @BatchSize(size = 500)
    @OneToMany(mappedBy = "boardComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecommendComment> recommendComments;  // 좋아요 리스트

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<BoardComment> children = new ArrayList<>();

    public BoardComment(UserEntity user, Board board, String content) {
        this.user=user;
        this.board=board;
        this.content=content;
    }

    public void updateParent(BoardComment parentComment) {
        this.parent=parentComment;
    }

    public BoardComment update(BoardCommentUpdateDTO boardCommentUpdateDTO) {
        this.content=boardCommentUpdateDTO.getContent();
        return this;
    }
}
