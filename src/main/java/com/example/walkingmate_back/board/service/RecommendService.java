package com.example.walkingmate_back.board.service;

import com.example.walkingmate_back.board.dto.BoardResponseDTO;
import com.example.walkingmate_back.board.entity.Board;
import com.example.walkingmate_back.board.entity.Recommend;
import com.example.walkingmate_back.board.repository.BoardRepository;
import com.example.walkingmate_back.board.repository.RecommendRepository;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *    좋아요 저장
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.08.04
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class RecommendService {

    private final RecommendRepository recommendRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    /**
     * 좋아요 존재 여부 확인 후 체크
     * - 전우진 2023.08.04
     */
    public BoardResponseDTO saveRecommend(Long boardId, String userId) {
        // 좋아요 존재 여부
        boolean existingRecommend = recommendRepository.existsByBoardIdAndUserId(boardId, userId);

        // 존재하는 경우 - 좋아요 - 1
        if(existingRecommend) {
            Board board = boardRepository.findById(boardId).orElse(null);
            if(board != null) {
                board.setRecommend(board.getRecommend() - 1);
            }

            recommendRepository.deleteByBoardIdAndUserId(boardId, userId);

            return BoardResponseDTO.builder()
                    .id(board.getId())
                    .userId(board.getUser().getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .recommend(board.getRecommend())
                    .build();
        }

        // 존재하지 않는 경우 - 좋아요 + 1
        UserEntity user = userRepository.findById(userId).orElse(null);
        Board board = boardRepository.findById(boardId).orElse(null);

        if(user == null || board == null) {
            return null;
        }

        board.setRecommend(board.getRecommend() + 1);

        Recommend newRecommend = new Recommend(user, board);
        recommendRepository.save(newRecommend);

        return BoardResponseDTO.builder()
                .id(board.getId())
                .userId(board.getUser().getId())
                .title(board.getTitle())
                .content(board.getContent())
                .recommend(board.getRecommend())
                .build();
    }
}
