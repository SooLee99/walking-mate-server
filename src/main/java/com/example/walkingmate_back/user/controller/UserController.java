package com.example.walkingmate_back.user.controller;

import com.example.walkingmate_back.user.dto.UserResponse;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.service.UserService;
import com.example.walkingmate_back.user.entity.UserService2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserService2 userService2;

    @PostMapping("/pwUpdate")
    public ResponseEntity<UserResponse> passwordUpdate(Authentication auth, @RequestBody String oldPw, @RequestBody String newPw) {

        return ResponseEntity.ok().body(userService.passwordUpdate(auth.getName(), oldPw, newPw));
    }

    @GetMapping("/info")
    public ResponseEntity<Optional<UserEntity>> userInfo(Authentication auth) {

        return ResponseEntity.ok().body(userService2.userInfo(auth.getName()));
    }

    @PostMapping("updateInfo")
    public ResponseEntity<UserResponse> updateInfo(Authentication auth, @RequestBody UserEntity user){

        return ResponseEntity.ok().body(userService2.updateInfo(auth.getName(), user));
    }


}
