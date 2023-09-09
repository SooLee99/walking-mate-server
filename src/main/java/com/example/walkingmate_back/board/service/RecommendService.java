package com.example.walkingmate_back.board.service;

import com.example.walkingmate_back.board.dto.BoardCommentResponseDTO;
import com.example.walkingmate_back.board.dto.BoardResponseDTO;
import com.example.walkingmate_back.board.entity.Board;
import com.example.walkingmate_back.board.entity.BoardComment;
import com.example.walkingmate_back.board.entity.Recommend;
import com.example.walkingmate_back.board.entity.RecommendComment;
import com.example.walkingmate_back.board.repository.BoardCommentRepository;
import com.example.walkingmate_back.board.repository.BoardRepository;
import com.example.walkingmate_back.board.repository.RecommendCommentRepository;
import com.example.walkingmate_back.board.repository.RecommendRepository;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *    게시글 좋아요, 댓글 좋아요 저장
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.08.30
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class RecommendService {

    private final RecommendRepository recommendRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final RecommendCommentRepository recommendCommentRepository;

    /**
     * 좋아요 존재 여부 확인 후 체크
     * - 전우진 2023.08.04
     */
    public BoardResponseDTO saveRecommend(Long boardId, String userId) {
        Board board = boardRepository.findById(boardId).orElse(null);
        if(board != null) {
            // 좋아요 존재 여부
            boolean existingRecommend = recommendRepository.existsByBoardIdAndUserId(boardId, userId);

            // 존재하는 경우 - 좋아요 - 1
            if(existingRecommend) {

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
                        .isrecommend(false)
                        .build();
            }

            // 존재하지 않는 경우 - 좋아요 + 1
            UserEntity user = userRepository.findById(userId).orElse(null);

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
                    .isrecommend(true)
                    .build();
        } else {
            return null;
        }
    }

    /**
     * 댓글 좋아요 존재 여부 확인 후 체크
     * - 전우진 2023.08.30
     */
    public BoardCommentResponseDTO saveRecommendComment(Long commentId, String userId) {
        BoardComment boardComment = boardCommentRepository.findById(commentId).orElse(null);
        if(boardComment != null) {
            // 좋아요 존재 여부
            boolean existingRecommend = recommendCommentRepository.existsByBoardCommentIdAndUserId(commentId, userId);

            // 존재하는 경우 - 좋아요 - 1
            if(existingRecommend) {

                if(boardComment != null) {
                    boardComment.setRecommend(boardComment.getRecommend() - 1);
                }

                recommendCommentRepository.deleteByBoardCommentIdAndUserId(commentId, userId);

                return BoardCommentResponseDTO.builder()
                        .id(boardComment.getId())
                        .boardId(boardComment.getBoard().getId())
                        .userId(boardComment.getUser().getId())
                        .content(boardComment.getContent())
                        .regTime(boardComment.getRegTime())
                        .updateTime(boardComment.getUpdateTime())
                        .recommend(boardComment.getRecommend())
                        .isrecommend(false)
                        .build();
            }

            // 존재하지 않는 경우 - 좋아요 + 1
            UserEntity user = userRepository.findById(userId).orElse(null);

            if(user == null || boardComment == null) {
                return null;
            }

            boardComment.setRecommend(boardComment.getRecommend() + 1);

            RecommendComment newRecommend = new RecommendComment(user, boardComment);
            recommendCommentRepository.save(newRecommend);

            return BoardCommentResponseDTO.builder()
                    .id(boardComment.getId())
                    .boardId(boardComment.getBoard().getId())
                    .userId(boardComment.getUser().getId())
                    .content(boardComment.getContent())
                    .regTime(boardComment.getRegTime())
                    .updateTime(boardComment.getUpdateTime())
                    .recommend(boardComment.getRecommend())
                    .isrecommend(true)
                    .build();
        } else {
            return null;
        }
    }
}
