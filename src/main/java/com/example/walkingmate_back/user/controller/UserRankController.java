package com.example.walkingmate_back.user.controller;

import com.example.walkingmate_back.main.response.DefaultRes;
import com.example.walkingmate_back.main.response.ResponseMessage;
import com.example.walkingmate_back.main.response.StatusEnum;
import com.example.walkingmate_back.user.dto.UserRankResponseDTO;
import com.example.walkingmate_back.user.service.UserRankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 *    사용자 개인 랭킹, 전체 랭킹 조회
 *
 *   @version          1.00 / 2023.07.21
 *   @author           전우진
 */

//@Controller
@RestController
@RequestMapping("/user/userRank")
public class UserRankController {

    private final UserRankService userRankService;

    public UserRankController(UserRankService userRankService) {
        this.userRankService = userRankService;
    }

    // 개인 랭킹 조회
    @GetMapping("/personal")
    public ResponseEntity<DefaultRes<UserRankResponseDTO>> SpecificationUserRank() {
        String userId = "aaa";
        UserRankResponseDTO userRankResponseDTO = userRankService.getUserRank(userId);

        if(userRankResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_SUCCESS, userRankResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USERRANK, null), HttpStatus.OK);
    }

    // 랭킹 전체 조회
    @GetMapping("/list")
    public ResponseEntity<DefaultRes<List<UserRankResponseDTO>>> listRank() {
        List<UserRankResponseDTO> userRankResponseDTO =  userRankService.getAllRank();

        if(userRankResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_SUCCESS, userRankResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USERRANK, null), HttpStatus.OK);
    }

}
