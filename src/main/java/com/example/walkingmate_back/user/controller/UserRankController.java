package com.example.walkingmate_back.user.controller;

import com.example.walkingmate_back.user.dto.UserRankResponseDTO;
import com.example.walkingmate_back.user.service.UserRankService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

/**
 *    사용자 개인 랭킹, 전체 랭킹 조회
 *
 *   @version          1.00 / 2023.07.14
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
    public Optional<UserRankResponseDTO> SpecificationUserRank() {
        String userId = "aaa";

        return userRankService.getUserRank(userId);
    }

    // 랭킹 전체 조회
    @GetMapping("/list")
    public List<UserRankResponseDTO> listRank() {

        return userRankService.getAllRank();
    }

}
