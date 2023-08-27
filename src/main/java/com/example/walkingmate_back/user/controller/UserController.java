package com.example.walkingmate_back.user.controller;

import com.example.walkingmate_back.main.response.DefaultRes;
import com.example.walkingmate_back.main.response.ResponseMessage;
import com.example.walkingmate_back.main.response.StatusEnum;
import com.example.walkingmate_back.user.dto.UserResponse;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    //private final UserService2 userService2;

    @PostMapping("/pwUpdate")
    public ResponseEntity<DefaultRes<UserResponse>> passwordUpdate(Authentication auth, @RequestBody String oldPw, @RequestBody String newPw) {
        UserResponse userResponse = userService.passwordUpdate(auth.getName(), oldPw, newPw);

        if(userResponse != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.PASSWORD_UPDATE_SUCCESS, userResponse), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.PASSWORD_UPDATE_FAIL), HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<DefaultRes<UserEntity>> userInfo(Authentication auth) {
        UserEntity userEntity = userService.FindUser(auth.getName());

        if(userEntity != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.USER_INFO_SEARCH, userEntity), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_USER_INFO), HttpStatus.OK);

    }

    @PostMapping("updateInfo")
    public ResponseEntity<DefaultRes<UserResponse>> updateInfo(Authentication auth, @RequestBody UserEntity user){
        UserResponse userResponse = userService.updateInfo(auth.getName(), user);

        if(userResponse != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.USER_UPDATE, userResponse), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.USER_UPDATE_FAIL), HttpStatus.OK);

    }


}
