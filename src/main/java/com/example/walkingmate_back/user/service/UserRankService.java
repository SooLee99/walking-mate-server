package com.example.walkingmate_back.user.service;

import com.example.walkingmate_back.user.dto.UserRankResponseDTO;
import com.example.walkingmate_back.user.entity.UserRank;
import com.example.walkingmate_back.user.repository.UserRankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *    사용자 개인 랭킹, 전체 랭킹 조회
 *
 *   @version          1.00 / 2023.07.14
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
    public Optional<UserRankResponseDTO> getUserRank(String userId) {
        Optional<UserRank> result = userRankRepository.findById(userId);

        if(result.isPresent()) {
            UserRank userRank = result.get();

            return Optional.of(new UserRankResponseDTO(
                    userRank.getUserId(),
                    userRank.getCoin(),
                    userRank.getTear()
            ));
        } else {
            return Optional.empty();
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
