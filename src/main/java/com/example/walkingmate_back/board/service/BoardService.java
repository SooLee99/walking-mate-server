package com.example.walkingmate_back.board.service;

import com.example.walkingmate_back.board.dto.BoardCommentResponseDTO;
import com.example.walkingmate_back.board.dto.BoardRequestDTO;
import com.example.walkingmate_back.board.dto.BoardResponseDTO;
import com.example.walkingmate_back.board.dto.BoardUpdateDTO;
import com.example.walkingmate_back.board.entity.Board;
import com.example.walkingmate_back.board.entity.BoardComment;
import com.example.walkingmate_back.board.repository.BoardRepository;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *    게시글 등록, 수정, 삭제, 조회
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.07.11
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
    public int saveBoard(BoardRequestDTO boardRequestDTO) {
        System.out.println(">>>>>>>" + boardRequestDTO.getUserId());
        // 유저 확인
        Optional<UserEntity> user = userRepository.findById(boardRequestDTO.getUserId());

        if(user != null) {
            Board board = new Board(user.get(), boardRequestDTO.getTitle(), boardRequestDTO.getContent());
            boardRepository.save(board);
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 게시글 탐색 후 게시글 삭제
     * - 전우진 2023.07.10
     */
    public int deleteBoard(Long id) {
        Board board = boardRepository.findById(id).orElse(null);

        if (board == null) {
            return -1;
        }
        boardRepository.delete(board);
        return 1;
    }

    /**
     * 게시글 탐색 후 게시글 수정
     * - 전우진 2023.07.10
     */
    public int updateBoard(Long id, BoardUpdateDTO boardUpdateDTO) {

        Optional<Board> result = boardRepository.findById(id);

        if (result == null) {
            return -1;
        }

        Board board = result.get();
        board.update(boardUpdateDTO);

        boardRepository.save(board);
        return 1;
    }

    /**
     * 단일 게시글 조회, 댓글 리턴
     * - 전우진 2023.07.11
     */
    public Optional<BoardResponseDTO> getBoard(Long id) {
        Optional<Board> result = boardRepository.findById(id);

        if(result.isPresent()) {
            Board board = result.get();

            // 댓글
            List<BoardComment> comments = board.getComments();

            List<BoardCommentResponseDTO> commentDTOList = comments.stream()
                    .map(comment -> new BoardCommentResponseDTO(comment.getId(), comment.getBoard().getId(), comment.getContent(), comment.getUser().getId(), comment.getRegTime(), comment.getUpdateTime()))
                    .collect(Collectors.toList());

            return Optional.of(new BoardResponseDTO(
                    board.getId(),
                    board.getUser().getId(),
                    board.getTitle(),
                    board.getContent(),
                    board.getRegTime(),
                    board.getUpdateTime(),
                    commentDTOList
                    ));
        } else {
            return Optional.empty();
        }
    }

    /**
     * 게시글 페이징 후 최근 값부터 댓글 포함 리턴
     * - 전우진 2023.07.11
     */
    public List<BoardResponseDTO> getAllBoard(int page) {

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
        return result;
    }
}
