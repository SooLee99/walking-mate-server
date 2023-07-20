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
 *    사용자 개인 랭킹, 전체 랭킹 조회
 *
 *   @version          1.00 / 2023.07.20
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

            UserRankResponseDTO userRankResponseDTO = new UserRankResponseDTO(
                    userRank.getUserId(),
                    userRank.getCoin(),
                    userRank.getTear()
            );

            return userRankResponseDTO.builder()
                    .userId(userRank.getUser().getId())
                    .coin(userRank.getCoin())
                    .tear(userRank.getTear())
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
        List<UserRank> userRanks = userRankRepository.findAll();
        List<UserRankResponseDTO> result = new ArrayList<>();

        for(UserRank userRank : userRanks) {
            UserRankResponseDTO rankResponseDTO = new UserRankResponseDTO(
                    userRank.getUserId(),
                    userRank.getCoin(),
                    userRank.getTear()
            );
            result.add(rankResponseDTO);
        }

        return result;
    }
}
