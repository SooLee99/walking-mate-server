package com.example.walkingmate_back.user.controller;

import com.example.walkingmate_back.user.dto.UserBodyResponseDTO;
import com.example.walkingmate_back.user.dto.UserBodyUpdateDTO;
import com.example.walkingmate_back.user.service.UserBodyService;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

/**
 *    사용자 신체정보 수정, 조회
 *
 *   @version          1.00 / 2023.07.14
 *   @author           전우진
 */


//@Controller
@RestController
@RequestMapping("/user/userBody")
public class UserBodyController {
    private final UserBodyService userBodyService;

    public UserBodyController(UserBodyService userBodyService) {
        this.userBodyService = userBodyService;
    }

    // 신체정보 조회, BMI 조회
    @GetMapping("/bodyInfo")
    public Optional<UserBodyResponseDTO> SpecificationUserBody() {
        String userId = "aaa";

        return userBodyService.getUserBody(userId);
    }

    // 신체정보 수정
    @PutMapping("/bodyInfo")
    public int updateUserBody(@RequestBody UserBodyUpdateDTO userBodyUpdateDTO) {
        String userId = "aaa";

        return userBodyService.updateUserBody(userBodyUpdateDTO, userId);
    }
}
