package com.example.walkingmate_back.board.service;

import com.example.walkingmate_back.board.dto.BoardCommentRequestDTO;
import com.example.walkingmate_back.board.entity.Board;
import com.example.walkingmate_back.board.entity.BoardComment;
import com.example.walkingmate_back.board.repository.BoardCommentRepository;
import com.example.walkingmate_back.board.repository.BoardRepository;
import com.example.walkingmate_back.main.entity.Message;
import com.example.walkingmate_back.main.entity.StatusEnum;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *    댓글 등록, 수정, 삭제
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.07.18
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
    public ResponseEntity<Message> saveComment(BoardCommentRequestDTO boardCommentRequestDTO) {
        Board board = boardRepository.findById(boardCommentRequestDTO.getBoardId()).orElse(null);

        UserEntity user = userRepository.findById(boardCommentRequestDTO.getUserId()).orElse(null);

        if(board == null || user == null) {
            // 사용자 또는 게시글이 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }

        BoardComment boardComment = new BoardComment(board, user, boardCommentRequestDTO.getContent());
        boardCommentRepository.save(boardComment);

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData("댓글 저장 성공");

        return ResponseEntity.ok().body(message);
    }

    /**
     * 게시글 탐색 후 댓글 수정
     * - 전우진 2023.07.11
     */
    public ResponseEntity<Message> updateComment(Long id, BoardCommentRequestDTO boardCommentRequestDTO) {
        BoardComment boardComment = boardCommentRepository.findById(id).orElse(null);

        if(boardComment == null) {
            // 댓글이 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }

        boardComment.update(boardCommentRequestDTO);

        boardCommentRepository.save(boardComment);

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData("댓글 수정 성공");

        return ResponseEntity.ok().body(message);
    }

    /**
     * 게시글 탐색 후 댓글 삭제
     * - 전우진 2023.07.11
     */
    public ResponseEntity<Message> deleteComment(Long id) {
        BoardComment boardComment = boardCommentRepository.findById(id).orElse(null);

        if(boardComment == null) {
            // 댓글이 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }

        boardCommentRepository.delete(boardComment);

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData("게시글 삭제 성공");

        return ResponseEntity.ok().body(message);
    }


}
