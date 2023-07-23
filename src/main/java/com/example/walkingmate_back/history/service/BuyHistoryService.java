package com.example.walkingmate_back.history.service;

import com.example.walkingmate_back.history.dto.BuyHistoryRequestDTO;
import com.example.walkingmate_back.history.dto.BuyHistoryResponseDTO;
import com.example.walkingmate_back.history.entity.BuyHistory;
import com.example.walkingmate_back.history.repository.BuyHistoryRepository;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.entity.UserRank;
import com.example.walkingmate_back.user.repository.UserRankRepository;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *    코인 구매, 구매내역 조회
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.07.23
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class BuyHistoryService {

    private final BuyHistoryRepository buyHistoryRepository;
    private final UserRepository userRepository;
    private final UserRankRepository userRankRepository;

    /**
     * 코인 구매
     * - 전우진 2023.07.23
     */
    public BuyHistoryResponseDTO saveBuyHistory(BuyHistoryRequestDTO buyHistoryRequestDTO) {
        UserEntity user = userRepository.findById(buyHistoryRequestDTO.getUserId()).orElse(null);
        LocalDateTime now = LocalDateTime.now();

        if (user != null) { // 사용자가 존재하는 경우
            BuyHistory buyHistory = new BuyHistory(user, now, buyHistoryRequestDTO.getCoin(), buyHistoryRequestDTO.getMoney());
            buyHistoryRepository.save(buyHistory);

            UserRank userRank = userRankRepository.findById(user.getId()).orElse(null);
            userRank.update(buyHistoryRequestDTO);
            userRankRepository.save(userRank);

            String date = buyHistory.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            return BuyHistoryResponseDTO.builder()
                    .id(buyHistory.getId())
                    .userId(buyHistory.getUser().getId())
                    .date(date)
                    .coin(buyHistory.getCoin())
                    .money(buyHistory.getMoney())
                    .build();
        } else {
            // 사용자가 존재하지 않는 경우
            return null;
        }
    }

    /**
     * 코인 구매내역 조회
     * - 전우진 2023.07.23
     */
    public List<BuyHistoryResponseDTO> getBuyHistory(String userId) {
        List<BuyHistory> buyHistories = buyHistoryRepository.findAllByUserId(userId);
        List<BuyHistoryResponseDTO> result = new ArrayList<>();

        for(BuyHistory buyHistory : buyHistories) {
            BuyHistoryResponseDTO buyHistoryResponseDTO = new BuyHistoryResponseDTO(
                    buyHistory.getId(),
                    buyHistory.getUser().getId(),
                    buyHistory.getDate().toString(),
                    buyHistory.getCoin(),
                    buyHistory.getMoney()
            );
            result.add(buyHistoryResponseDTO);
        }
        return result;
    }
}
