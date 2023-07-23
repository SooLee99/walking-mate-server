package com.example.walkingmate_back.login.service;

import com.example.walkingmate_back.login.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class LoginService {
    @Value("${jwt.secret}")
    private String secretKey;
    // Token 유효 시간 설정
    private Long expiredMs = 1000 * 60 * 60l;
    // Controller에서 받아온 id와 선언해둔 secretKey, expiredMs를 태워
    // util의 createJwt메서드를 호출하여 토큰을 만들어옴
    public String login(String userName, String password) {
        // 인증과정 생략
        log.info("userName:{}, password:{}", userName, password);
        return JwtUtil.createJwt(userName, secretKey, expiredMs);
    }
}