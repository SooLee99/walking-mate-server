package com.example.walkingmate_back.history.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckListResponseDTO {

    private Long listId;  // 체크리스트 id

    private String userId;  // 사용자 id

    private String date;  // 작성 날짜

    private boolean checked;  // 체크 여부

    private String content;  // 내용

    public CheckListResponseDTO(Long listId, String userId, String date, boolean checked, String content) {
        this.listId=listId;
        this.userId=userId;
        this.date=date;
        this.checked=checked;
        this.content=content;
    }

}
