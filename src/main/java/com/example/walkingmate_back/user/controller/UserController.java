package com.example.walkingmate_back.user.controller;

import com.example.walkingmate_back.login.utils.JwtUtil;
import com.example.walkingmate_back.user.dto.UserResponse;
import com.example.walkingmate_back.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/pwUpdate")
    public ResponseEntity<UserResponse> passwordUpdate(Authentication authentication, @RequestBody String oldPw, @RequestBody String newPw) {

        return ResponseEntity.ok().body(userService.passwordUpdate("ib.lee", oldPw, newPw));
    }


}
