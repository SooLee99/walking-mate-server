package com.example.walkingmate_back.history.entity;

import com.example.walkingmate_back.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@IdClass(CheckListId.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "checkList")
public class CheckList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listId; // 체크리스트 번호 (자동 증가)

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // 외래키 설정
    private UserEntity user;  // 사용자 id

    @Id
    @Column
    private Date date;  // 작성 날짜

    @Column
    private boolean checked;  // 체크 여부

    @Column
    private String content;  // 내용
}
