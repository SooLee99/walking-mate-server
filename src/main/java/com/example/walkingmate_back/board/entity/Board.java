package com.example.walkingmate_back.board.entity;

import com.example.walkingmate_back.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@IdClass(BoardId.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게시판 번호 (자동 증가)

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // 외래키 설정
    private UserEntity user;  // 사용자 id

    @Id
    @Column
    private Date date;  // 작성 날짜

    @Column
    private String title;  // 제목

    @Column
    private String content;  // 내용
}
