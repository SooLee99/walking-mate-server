package com.example.walkingmate_back.board.entity;

import com.example.walkingmate_back.board.dto.BoardUpdateDTO;
import com.example.walkingmate_back.main.entity.BaseTimeEntity;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board")
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id; // 게시판 번호 (자동 증가)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // 외래키 설정
    private UserEntity user;  // 사용자 id

    // 시간 빼둠

    @Column
    private String title;  // 제목

    @Column
    private String content;  // 내용

    @JsonIgnore
    @BatchSize(size = 500)
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardComment> comments;  // 댓글 리스트

    public Board(UserEntity user, String title, String content) {
        this.user=user;
        this.title=title;
        this.content=content;
    }

    // 게시글 수정
    public Board update(BoardUpdateDTO boardUpdateDTO) {
        this.title=boardUpdateDTO.getTitle();
        this.content=boardUpdateDTO.getContent();
        return this;
    }
}
