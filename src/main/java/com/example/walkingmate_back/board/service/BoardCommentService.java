package com.example.walkingmate_back.board.service;

import com.example.walkingmate_back.board.dto.*;
import com.example.walkingmate_back.board.entity.Board;
import com.example.walkingmate_back.board.entity.BoardComment;
import com.example.walkingmate_back.board.repository.BoardCommentRepository;
import com.example.walkingmate_back.board.repository.BoardRepository;
import com.example.walkingmate_back.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *    댓글 등록, 수정, 삭제 - 대댓글 포함
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.08.07
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class BoardCommentService {
    private final BoardCommentRepository boardCommentRepository;
    private final BoardRepository boardRepository;

    /**
     * 게시글 & 사용자 확인 후 댓글 저장
     * - 전우진 2023.08.07
     */
    public BoardCommentResponseDTO saveCommemt(BoardCommentRequestDTO boardCommentRequestDTO, UserEntity user, Long boardId) {

        Board board = boardRepository.findById(boardId).orElse(null);
        if(board == null) return null; // 게시글이 존재하지 않는 경우

        BoardComment boardComment = new BoardComment(user, board, boardCommentRequestDTO.getContent());

        BoardComment parentComment;
        Long parentId = 0L;
        if(boardCommentRequestDTO.getParentId() != null) { // 부모 댓글이 있는 경우
            parentComment = boardCommentRepository.findById(boardCommentRequestDTO.getParentId()).orElse(null);

            boardComment.updateParent(parentComment);
            parentId = parentComment.getId();
        }

        boardCommentRepository.save(boardComment);

        return BoardCommentResponseDTO.builder()
                .id(boardComment.getId())
                .boardId(boardComment.getBoard().getId())
                .userId(boardComment.getUser().getId())
                .content(boardComment.getContent())
                .parentId(parentId)
                .regTime(boardComment.getRegTime())
                .updateTime(boardComment.getUpdateTime())
                .build();
    }

    /**
     * 게시글 탐색 후 댓글 수정
     * - 전우진 2023.08.07
     */
    public BoardCommentResponseDTO updateComment(BoardComment boardComment, BoardCommentUpdateDTO boardCommentUpdateDTO, String userId) {
        if(userId.equals(boardComment.getUser().getId())) {
            boardComment.update(boardCommentUpdateDTO);
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
     * - 전우진 2023.08.07
     */
    public BoardCommentResponseDTO deleteComment(BoardComment boardComment, String userId) {

        if(boardComment.getChildren().size() != 0) { // 자식 있는 경우
            boardCommentRepository.deleteAllByParentId(boardComment.getId());
            boardCommentRepository.delete(boardComment);
        } else {
            boardCommentRepository.delete(boardComment);
        }
        return BoardCommentResponseDTO.builder()
                .id(boardComment.getId())
                .boardId(boardComment.getBoard().getId())
                .userId(boardComment.getUser().getId())
                .content(boardComment.getContent())
                .regTime(boardComment.getRegTime())
                .updateTime(boardComment.getUpdateTime())
                .build();
    }

    public BoardComment FindBoardComment(Long id){
        return boardCommentRepository.findById(id).orElse(null);
    }

}
