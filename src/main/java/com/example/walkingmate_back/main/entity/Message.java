package com.example.walkingmate_back.main.entity;

import lombok.Data;

// 상태코드, 메시지, 데이터를 담을 클래스
@Data
public class Message {
    private StatusEnum status;
    private String message;
    private Object data;

    public Message() {
        this.status = StatusEnum.BAD_REQUEST;
        this.data = null;
        this.message = null;
    }
}
