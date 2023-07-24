package com.example.walkingmate_back.board.service;

import com.example.walkingmate_back.board.dto.BoardCommentRequestDTO;
import com.example.walkingmate_back.board.dto.BoardCommentResponseDTO;
import com.example.walkingmate_back.board.entity.Board;
import com.example.walkingmate_back.board.entity.BoardComment;
import com.example.walkingmate_back.board.repository.BoardCommentRepository;
import com.example.walkingmate_back.board.repository.BoardRepository;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *    댓글 등록, 수정, 삭제
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.07.24
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class BoardCommentService {

    private final BoardCommentRepository boardCommentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    /**
     * 게시글 & 사용자 확인 후 댓글 저장
     * - 전우진 2023.07.11
     */
    public BoardCommentResponseDTO saveComment(BoardCommentRequestDTO boardCommentRequestDTO, UserEntity user) {
        Board board = boardRepository.findById(boardCommentRequestDTO.getBoardId()).orElse(null);

        if(board == null) {
            // 게시글이 존재하지 않는 경우
            return null;
        }

        BoardComment boardComment = new BoardComment(board, user, boardCommentRequestDTO.getContent());
        boardCommentRepository.save(boardComment);

        return BoardCommentResponseDTO.builder()
                .id(boardComment.getId())
                .boardId(boardComment.getBoard().getId())
                .userId(boardComment.getUser().getId())
                .content(boardComment.getContent())
                .regTime(boardComment.getRegTime())
                .updateTime(boardComment.getUpdateTime())
                .build();
    }

    /**
     * 게시글 탐색 후 댓글 수정
     * - 전우진 2023.07.11
     */
    public BoardCommentResponseDTO updateComment(BoardComment boardComment, BoardCommentRequestDTO boardCommentRequestDTO, String userId) {

        if(userId.equals(boardComment.getUser().getId())) {
            boardComment.update(boardCommentRequestDTO);
            boardCommentRepository.save(boardComment);

            return BoardCommentResponseDTO.builder()
                    .id(boardComment.getId())
                    .boardId(boardComment.getBoard().getId())
                    .userId(boardComment.getUser().getId())
                    .content(boardComment.getContent())
                    .regTime(boardComment.getRegTime())
                    .updateTime(boardComment.getUpdateTime())
                    .build();
        }
       return null;
    }

    /**
     * 게시글 탐색 후 댓글 삭제
     * - 전우진 2023.07.11
     */
    public BoardCommentResponseDTO deleteComment(BoardComment boardComment, String userId) {
        if(userId.equals(boardComment.getUser().getId())) {
            boardCommentRepository.delete(boardComment);

            return BoardCommentResponseDTO.builder()
                    .id(boardComment.getId())
                    .boardId(boardComment.getBoard().getId())
                    .userId(boardComment.getUser().getId())
                    .content(boardComment.getContent())
                    .regTime(boardComment.getRegTime())
                    .updateTime(boardComment.getUpdateTime())
                    .build();
        }
       return null;
    }

    public BoardComment FindBoardComment(Long id){
        return boardCommentRepository.findById(id).orElse(null);
    }
}
