//package com.example.walkingmate_back.user.controller;
//
//import com.example.walkingmate_back.main.response.DefaultRes;
//import com.example.walkingmate_back.main.response.ResponseMessage;
//import com.example.walkingmate_back.main.response.StatusEnum;
//import com.example.walkingmate_back.user.dto.UserBodyResponseDTO;
//import com.example.walkingmate_back.user.dto.UserBodyUpdateDTO;
//import com.example.walkingmate_back.user.service.UserBodyService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
///**
// *    사용자 신체정보 수정, 조회
// *
// *   @version          1.00 / 2023.07.21
// *   @author           전우진
// */
//
////@Controller
//@RestController
//@RequestMapping("/user/userBody")
//public class UserBodyController {
//
//    private final UserBodyService userBodyService;
//
//    public UserBodyController(UserBodyService userBodyService) {
//        this.userBodyService = userBodyService;
//    }
//
//    // 신체정보 조회, BMI 조회
//    @GetMapping("/bodyInfo")
//    public ResponseEntity<DefaultRes<UserBodyResponseDTO>> SpecificationUserBody() {
//        String userId = "aaa";
//
//        UserBodyResponseDTO userBodyResponseDTO = userBodyService.getUserBody(userId);
//
//        if(userBodyResponseDTO != null)
//            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_SUCCESS, userBodyResponseDTO), HttpStatus.OK);
//        else
//            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USERBODY, null), HttpStatus.OK);
//    }
//
//
//    // 신체정보 수정
//    @PutMapping("/bodyInfo")
//    public ResponseEntity<DefaultRes<UserBodyResponseDTO>> updateUserBody(@RequestBody UserBodyUpdateDTO userBodyUpdateDTO) {
//        String userId = "aaa";
//
//        UserBodyResponseDTO userBodyResponseDTO = userBodyService.updateUserBody(userBodyUpdateDTO, userId);
//
//        if(userBodyResponseDTO != null)
//            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.UPDATE_USERBODY, userBodyResponseDTO), HttpStatus.OK);
//        else
//            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USERBODY, null), HttpStatus.OK);
//    }
//}
