package com.example.walkingmate_back.login.controller;

import com.example.walkingmate_back.login.dto.LoginRequest;
import com.example.walkingmate_back.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    // login 버튼 클릭 시 id, pw를 받으며 호출됨.
    // dto로 (id,pw)값을 태워서 service의 login메서드 호출
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest dto) {
        return ResponseEntity.ok().body(loginService.login(dto.getUserName(), dto.getPassword()));
    }
    @PostMapping("/join")
    public ResponseEntity<String> join() {
        return ResponseEntity.ok().body("회원가입 완료");
    }
}