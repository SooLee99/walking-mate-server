package com.example.walkingmate_back.user.controller;

import com.example.walkingmate_back.main.response.DefaultRes;
import com.example.walkingmate_back.main.response.ResponseMessage;
import com.example.walkingmate_back.main.response.StatusEnum;
import com.example.walkingmate_back.user.dto.User;
import com.example.walkingmate_back.user.dto.UserResponse;
import com.example.walkingmate_back.user.dto.UserUpdateDTO;
import com.example.walkingmate_back.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 *    사용자 정보 수정, 조회, 탈퇴, 비밀번호 재설정
 *
 *   @version          1.00 / 2023.09.10
 *   @author           전우진, 이인범
 */

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 비밀번호 재설정
    @GetMapping("/pwUpdate")
    public ResponseEntity<DefaultRes<UserResponse>> passwordUpdate(Authentication auth, @RequestParam String oldPw, @RequestParam String newPw) {
        UserResponse userResponse = userService.passwordUpdate(auth.getName(), oldPw, newPw);

        if(userResponse != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.PASSWORD_UPDATE_SUCCESS, userResponse), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.PASSWORD_UPDATE_FAIL), HttpStatus.OK);
    }

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

}
