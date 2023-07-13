package com.example.walkingmate_back.history.entity;

import com.example.walkingmate_back.history.dto.CheckListRequestDTO;
import com.example.walkingmate_back.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "checkList")
public class CheckList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listId; // 체크리스트 번호 (자동 증가)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // 외래키 설정
    private UserEntity user;  // 사용자 id

    @Column
    private LocalDate date;  // 작성 날짜

    @Column
    private boolean checked;  // 체크 여부

    @Column
    private String content;  // 내용

    public CheckList(UserEntity userEntity, LocalDate date, String content, boolean checked) {
        this.user=userEntity;
        this.date=date;
        this.content=content;
        this.checked=checked;
    }

    public CheckList updateCheckd(boolean checked) {
        this.checked=checked;
        return this;

    }

    public CheckList update(CheckListRequestDTO checkListRequestDTO, LocalDate date) {
        this.content=checkListRequestDTO.getContent();
        this.date=date;
        return this;
    }
}
