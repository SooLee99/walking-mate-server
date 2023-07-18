package com.example.walkingmate_back.board.service;

import com.example.walkingmate_back.board.dto.BoardCommentResponseDTO;
import com.example.walkingmate_back.board.dto.BoardRequestDTO;
import com.example.walkingmate_back.board.dto.BoardResponseDTO;
import com.example.walkingmate_back.board.dto.BoardUpdateDTO;
import com.example.walkingmate_back.board.entity.Board;
import com.example.walkingmate_back.board.entity.BoardComment;
import com.example.walkingmate_back.board.repository.BoardRepository;
import com.example.walkingmate_back.main.entity.Message;
import com.example.walkingmate_back.main.entity.StatusEnum;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *    게시글 등록, 수정, 삭제, 단일 조회, 전체 조회
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.07.18
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    /**
     * 사용자 확인 후 게시글 저장
     * - 전우진 2023.07.10
     */
    public ResponseEntity<Message> saveBoard(BoardRequestDTO boardRequestDTO) {
        UserEntity user = userRepository.findById(boardRequestDTO.getUserId()).orElse(null);

        if(user != null) { // 사용자가 존재하는 경우
            Board board = new Board(user, boardRequestDTO.getTitle(), boardRequestDTO.getContent());
            boardRepository.save(board);

            Message message = new Message();
            message.setStatus(StatusEnum.OK);
            message.setMessage("성공 코드");
            message.setData("게시글 저장 성공");

            return ResponseEntity.ok().body(message);
        } else {
            // 사용자가 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 게시글 탐색 후 게시글 수정
     * - 전우진 2023.07.10
     */
    public ResponseEntity<Message> updateBoard(Long id, BoardUpdateDTO boardUpdateDTO) {

        Board board = boardRepository.findById(id).orElse(null);

        if (board == null) {
            // 게시글이 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }

        board.update(boardUpdateDTO);
        boardRepository.save(board);

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData("게시글 수정 성공");

        return ResponseEntity.ok().body(message);
    }

    /**
     * 게시글 탐색 후 게시글 삭제
     * - 전우진 2023.07.10
     */
    public ResponseEntity<Message> deleteBoard(Long id) {
        Board board = boardRepository.findById(id).orElse(null);

        if (board == null) {
            // 게시글이 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }

        boardRepository.delete(board);

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData("게시글 삭제 성공");

        return ResponseEntity.ok().body(message);
    }

    /**
     * 단일 게시글 조회, 댓글 리턴
     * - 전우진 2023.07.11
     */
    public ResponseEntity<Message> getBoard(Long id) {
        Board board = boardRepository.findById(id).orElse(null);

        if(board != null) { // 게시글이 존재하는 경우
            // 댓글
            List<BoardComment> comments = board.getComments();

            List<BoardCommentResponseDTO> commentDTOList = comments.stream()
                    .map(comment -> new BoardCommentResponseDTO(comment.getId(), comment.getBoard().getId(), comment.getContent(), comment.getUser().getId(), comment.getRegTime(), comment.getUpdateTime()))
                    .collect(Collectors.toList());

            BoardResponseDTO boardResponseDTO = new BoardResponseDTO(
                    board.getId(),
                    board.getUser().getId(),
                    board.getTitle(),
                    board.getContent(),
                    board.getRegTime(),
                    board.getUpdateTime(),
                    commentDTOList
                    );

            Message message = new Message();
            message.setStatus(StatusEnum.OK);
            message.setMessage("성공 코드");
            message.setData(boardResponseDTO);

            return ResponseEntity.ok().body(message);
        } else {
            // 게시글이 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 게시글 페이징 후 최근 값부터 댓글 포함 리턴
     * - 전우진 2023.07.11
     */
    public ResponseEntity<Message> getAllBoard(int page) {

        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("id").descending());

        List<Board> boards = boardRepository.findAllBoard(pageable);
        List<BoardResponseDTO> result = new ArrayList<>();

        for (Board board : boards) {

            List<BoardCommentResponseDTO> commentDTOList = board.getComments().stream()
                    .map(comment -> new BoardCommentResponseDTO(comment.getId(), comment.getBoard().getId(), comment.getContent(), comment.getUser().getId(), comment.getRegTime(), comment.getUpdateTime()))
                    .collect(Collectors.toList());

            BoardResponseDTO boardResponseDTO = new BoardResponseDTO(
                    board.getId(),
                    board.getUser().getId(),
                    board.getTitle(),
                    board.getContent(),
                    board.getRegTime(),
                    board.getUpdateTime(),
                    commentDTOList
            );

            result.add(boardResponseDTO);
        }

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData(result);

        return ResponseEntity.ok().body(message);
    }
}
