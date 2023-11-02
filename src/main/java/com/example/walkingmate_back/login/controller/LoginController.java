package com.example.walkingmate_back.login.controller;

import com.example.walkingmate_back.login.domain.JoinRequest;
import com.example.walkingmate_back.login.domain.JoinResponseDTO;
import com.example.walkingmate_back.login.domain.LoginRequest;
import com.example.walkingmate_back.login.domain.LoginResponse;
import com.example.walkingmate_back.login.service.LoginService;
import com.example.walkingmate_back.main.response.DefaultRes;
import com.example.walkingmate_back.main.response.ResponseMessage;
import com.example.walkingmate_back.main.response.StatusEnum;
import com.example.walkingmate_back.user.dto.EmailDTO;
import com.example.walkingmate_back.user.dto.UserEmailConfirmDTO;
import com.example.walkingmate_back.user.dto.UserPwConfirmDTO;
import com.example.walkingmate_back.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *    로그인, 회원가입
 *
 *   @version          1.00 / 2023.09.10
 *   @author           전우진
 */

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final LoginService loginService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<DefaultRes<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = loginService.login(loginRequest);

        if(loginResponse != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.LOGIN_MESSAGE, loginResponse), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.LOGIN_FAIL), HttpStatus.OK);

    }

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<DefaultRes<JoinResponseDTO>> join(@RequestBody JoinRequest joinRequest) {
        JoinResponseDTO joinResponseDTO = loginService.join(joinRequest);
        if(joinResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.JOIN_MESSAGE, joinResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.JOIN_FAIL, joinResponseDTO), HttpStatus.OK);
    }

    // 회원가입 시 이메일 전송 (2023-09-20 이수 수정함.) <- 시간 없어서 코드 수정 작성함.
    @PostMapping("/emailConfirm")
    public ResponseEntity<DefaultRes<String>> mailConfirm(@RequestBody EmailDTO email) {
        log.info("회원가입 시, 이메일 인증번호 요청이 들어옴 ->" + email.getEmail());

        // TODO: 입력된 이메일이 현재 가입된 이메일인지 체크한다.
        UserEntity user = userService.getUserInfo(String.valueOf(email.getEmail()));
        int num = 0;
        log.info("회원 여부 조회 결과 -> " + user);

        if (user != null) {
            // TODO: 입력된 이메일이 현재 회원으로 존재하는 이메일이라면?
            log.info("현재 존재하는 이메일임");
            log.info(user.toString());
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, "현재 가입된 회원 이메일입니다.",num+""), HttpStatus.OK);
        }
        else {
            // TODO: DB에 존재하지 않는 이메일 -> 이메일에 인증번호 전송 시작하기
            log.info("이메일 인증 시작 -> "+ email.getEmail());
            num = emailService.sendEmail(email.getEmail());
            log.info("추출된 인증번호 : " + num);
            if(num == 0) {
                // TODO: 인증번호 전송 실패.
                log.info("인증번호 추출 실패");
                return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, "인증번호 추출에 실패하였습니다.", num+""), HttpStatus.OK);
            } else {
                // TODO: 인증번호 전송 성공.
                log.info("인증번호 전송 성공");
                return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, "인증번호를 전송하였습니다.", num+""), HttpStatus.OK);
            }
        }
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
}