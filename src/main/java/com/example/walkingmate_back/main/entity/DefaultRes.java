package com.example.walkingmate_back.main.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

// 상태코드, 메시지, 데이터를 담을 클래스
@Data
@AllArgsConstructor
@Builder
public class DefaultRes<T> {
    private StatusEnum status;
    private String message;
    private T data;

    public DefaultRes(StatusEnum status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }

    public static<T> DefaultRes<T> res(StatusEnum status, String message) {
        return res(status, message, null);
    }

    public static<T> DefaultRes<T> res(StatusEnum status, String message, T data) {
        return DefaultRes.<T>builder()
                .data(data)
                .status(status)
                .message(message)
                .build();
    }
}
