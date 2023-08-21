package com.example.walkingmate_back.user.service;

import com.example.walkingmate_back.user.dto.UserRankResponseDTO;
import com.example.walkingmate_back.user.entity.UserRank;
import com.example.walkingmate_back.user.repository.UserRankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 *    사용자 개인 랭킹, 전체 랭킹 조회, 티어 수정
 *
 *   @version          1.00 / 2023.08.09
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class UserRankService {

    private final UserRankRepository userRankRepository;

    /**
     * 사용자 확인 후 개인 랭킹 조회
     * - 전우진 2023.07.14
     */
    public UserRankResponseDTO getUserRank(String userId) {
        UserRank userRank = userRankRepository.findById(userId).orElse(null);

        if(userRank != null) {  // 사용자 랭킹 정보가 존재하는 경우

            return UserRankResponseDTO.builder()
                    .userId(userRank.getUser().getId())
                    .coin(userRank.getCoin())
                    .tier(userRank.getTier())
                    .runNum(userRank.getRunNum())
                    .build();
        } else {
            return null;
        }
    }

    /**
     * 전체 랭킹 조회
     * - 전우진 2023.07.14
     */
    public List<UserRankResponseDTO> getAllRank() {
        List<UserRank> userRanks = userRankRepository.findAllByOrderByRunNumDesc();
        List<UserRankResponseDTO> result = new ArrayList<>();

        for(UserRank userRank : userRanks) {
            UserRankResponseDTO rankResponseDTO = new UserRankResponseDTO(
                    userRank.getUserId(),
                    userRank.getCoin(),
                    userRank.getTier(),
                    userRank.getRunNum()
            );
            result.add(rankResponseDTO);
        }

        return result;
    }

    /**
     * 티어 수정
     * - 전우진 2023.08.09
     */
    public UserRankResponseDTO updateUserRank(String userId) {
        UserRank userRank = userRankRepository.findById(userId).orElse(null);
        int num = userRank.getRunNum();

        // 아이언, 브론즈, 실버, 골드, 플래티넘, 다이아몬드, 마스터, 챌린저
        String tier;
        if(num >= 100000) tier = "챌린저";  // 100,000
        else if(100000 > num && num > 49999) tier = "마스터"; // 50,000 ~ 99,999
        else if(50000 > num && num > 9999) tier = "다이아몬드";  // 10,000 ~ 49,999
        else if(10000 > num && num > 4999) tier = "플래티넘";  // 5,000 ~ 9,999
        else if(5000 > num && num > 2499) tier = "골드";  // 2,500 ~ 4,999
        else if(2500 > num && num > 999) tier = "실버";  // 1,000 ~ 2,499
        else if(1000 > num && num > 99) tier = "브론즈";  // 100 ~ 999
        else tier = "아이언";  // 0 ~ 99

        userRank.update(tier);
        userRankRepository.save(userRank);

        return UserRankResponseDTO.builder()
                .userId(userId)
                .coin(userRank.getCoin())
                .tier(userRank.getTier())
                .runNum(userRank.getRunNum())
                .build();
    }
}
