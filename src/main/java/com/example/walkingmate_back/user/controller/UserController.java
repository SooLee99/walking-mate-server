package com.example.walkingmate_back.user.controller;

import com.example.walkingmate_back.login.domain.JoinRequest;
import com.example.walkingmate_back.login.service.EmailService;
import com.example.walkingmate_back.main.response.DefaultRes;
import com.example.walkingmate_back.main.response.ResponseMessage;
import com.example.walkingmate_back.main.response.StatusEnum;
import com.example.walkingmate_back.user.dto.*;
import com.example.walkingmate_back.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 *    사용자 정보 수정, 조회, 탈퇴, 비밀번호 재설정
 *
 *   @version          1.00 / 2023.09.11
 *   @author           전우진, 이인범
 */

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 사용자 정보 조회
    @GetMapping("/getInfo")
    public ResponseEntity<DefaultRes<User>> getInfo(Authentication auth) {
        User user = userService.getInfo(auth.getName());

        if (user != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.USER_INFO_SEARCH, user), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_USER_INFO), HttpStatus.OK);

    }

    // 사용자 정보 수정
    @PostMapping("/updateInfo")
    public ResponseEntity<DefaultRes<User>> updateInfo(Authentication auth, @RequestBody UserUpdateDTO userUpdateDTO){
        User user = userService.updateInfo(auth.getName(), userUpdateDTO);

        if(user != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.USER_UPDATE, user), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.USER_UPDATE_FAIL), HttpStatus.OK);

    }

    // 사용자 탈퇴
    @DeleteMapping("/withdrawal")
    public ResponseEntity<DefaultRes<UserResponse>> withdrawalUser(Authentication authentication) {
        UserResponse userResponse = userService.withdrawalUser(authentication.getName());

        if(userResponse != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.USER_WITHDRAWAL_SUCCESS, userResponse), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER), HttpStatus.OK);

    }


    private final EmailService emailService;

    // 회원가입 시 이메일 전송
    @PostMapping("/emailConfirm")
    public ResponseEntity<DefaultRes<String>> mailConfirm(@RequestBody JoinRequest joinRequest) {
        int num = emailService.sendEmail(joinRequest.getId());

        return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.USER_EMAIL_SUCESS, ""+num), HttpStatus.OK);
    }

    // 인증번호 일치 여부 확인
    @PostMapping("/numberConfirm")
    public ResponseEntity<DefaultRes<String>> numberConfirm(@RequestBody UserEmailConfirmDTO userEmailConfirmDTO) {
        if(userEmailConfirmDTO.getUserNumber() == userEmailConfirmDTO.getEmailNumber())
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.USER_NUMBER_TRUE, "인증번호가 일치합니다."), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.USER_NUMBER_FALSE, "인증번호가 일치하지 않습니다."), HttpStatus.OK);
    }

    // 비밀번호 재설정 시 이메일 전송
    @PostMapping("/passwordConfirm")
    public ResponseEntity<DefaultRes<String>> passwordConfirm(@RequestBody UserPwConfirmDTO userPwConfirmDTO) {
        int num = emailService.sendPwEmail(userPwConfirmDTO.getUserId());

        if(num == -1) {
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER, "존재하지 않는 사용자입니다."), HttpStatus.OK);
        } else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.USER_EMAIL_SUCESS, ""+num), HttpStatus.OK);
    }

    // 비밀번호 재설정
    @PutMapping("/pwUpdate")
    public ResponseEntity<DefaultRes<UserResponse>> passwordUpdate(@RequestBody UserPwConfirmDTO userPwConfirmDTO) {
        UserResponse userResponse = userService.passwordUpdate(userPwConfirmDTO);

        if(userResponse != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.PASSWORD_UPDATE, userResponse), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.PASSWORD_UPDATE_FAIL), HttpStatus.OK);
    }
}
