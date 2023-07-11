package com.example.walkingmate_back.board.service;

import com.example.walkingmate_back.board.dto.BoardCommentRequestDTO;
import com.example.walkingmate_back.board.entity.Board;
import com.example.walkingmate_back.board.entity.BoardComment;
import com.example.walkingmate_back.board.repository.BoardCommentRepository;
import com.example.walkingmate_back.board.repository.BoardRepository;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 *    댓글 등록, 수정, 삭제
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.07.11
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
    public int saveComment(BoardCommentRequestDTO boardCommentRequestDTO) {
        Optional<Board> board = boardRepository.findById(boardCommentRequestDTO.getBoardId());
        // 유저 확인
        Optional<UserEntity> user = userRepository.findById(boardCommentRequestDTO.getUserId());

        if(board.isEmpty() || user.isEmpty()) {
            return -2; // 사용자 또는 게시글이 존재하지 않는 경우
        }

        BoardComment boardComment = new BoardComment(board.get(), user.get(), boardCommentRequestDTO.getContent());
        boardCommentRepository.save(boardComment);
        return 1;
    }

    /**
     * 게시글 탐색 후 댓글 수정
     * - 전우진 2023.07.11
     */
    public int updateComment(Long id, BoardCommentRequestDTO boardCommentRequestDTO) {
        Optional<BoardComment> result = boardCommentRepository.findById(id);
        if(result == null) {
            return  -1;
        }

        BoardComment boardComment = result.get();
        boardComment.update(boardCommentRequestDTO);

        boardCommentRepository.save(boardComment);
        return 1;
    }

    /**
     * 게시글 탐색 후 댓글 삭제
     * - 전우진 2023.07.11
     */
    public int deleteComment(Long id) {
        BoardComment boardComment = boardCommentRepository.findById(id).orElse(null);

        if(boardComment == null) {
            return -1;
        }

        boardCommentRepository.delete(boardComment);
        return 1;
    }


}
