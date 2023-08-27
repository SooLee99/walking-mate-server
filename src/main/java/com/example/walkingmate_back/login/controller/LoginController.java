package com.example.walkingmate_back.login.controller;

import com.example.walkingmate_back.board.dto.BoardResponseDTO;
import com.example.walkingmate_back.login.domain.JoinRequest;
import com.example.walkingmate_back.login.domain.LoginRequest;
import com.example.walkingmate_back.login.domain.LoginResponse;
import com.example.walkingmate_back.login.service.LoginService;
import com.example.walkingmate_back.main.response.DefaultRes;
import com.example.walkingmate_back.main.response.ResponseMessage;
import com.example.walkingmate_back.main.response.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<DefaultRes<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = loginService.login(loginRequest);

        if(loginResponse != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.LOGIN_SUCCESS, loginResponse), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.LOGIN_FAIL), HttpStatus.OK);

    }
    @PostMapping("/join")
    public ResponseEntity<DefaultRes<Void>> join(@RequestBody JoinRequest joinRequest) {
        int joinCheck = loginService.join(joinRequest);

        if(joinCheck == 1)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.JOIN_SUCCESS), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.JOIN_FAIL), HttpStatus.OK);
    }
}