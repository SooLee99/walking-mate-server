package com.example.walkingmate_back.board.service;

import com.example.walkingmate_back.board.dto.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *    게시글 등록, 수정, 삭제, 단일 조회, 전체 조회
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.08.07
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
    public BoardResponseDTO saveBoard(BoardRequestDTO boardRequestDTO, String userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);

        if(user != null) { // 사용자가 존재하는 경우
            Board board = new Board(user, boardRequestDTO.getTitle(), boardRequestDTO.getContent());
            boardRepository.save(board);

            return BoardResponseDTO.builder()
                    .id(board.getId())
                    .userId(board.getUser().getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .regTime(board.getRegTime())
                    .updateTime(board.getUpdateTime())
                    .recommend(board.getRecommend())
                    .build();
        } else {
            // 사용자가 존재하지 않는 경우
            return null;
        }
    }

    /**
     * 게시글 탐색 후 게시글 수정
     * - 전우진 2023.07.10
     */
    public BoardResponseDTO updateBoard(Board board, BoardUpdateDTO boardUpdateDTO, String userId) {

        if(userId.equals(board.getUser().getId())) {
            board.update(boardUpdateDTO);
            boardRepository.save(board);

            return BoardResponseDTO.builder()
                    .id(board.getId())
                    .userId(board.getUser().getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .regTime(board.getRegTime())
                    .updateTime(board.getUpdateTime())
                    .recommend(board.getRecommend())
                    .build();
        }
        return null;
    }

    /**
     * 게시글 탐색 후 게시글 삭제
     * - 전우진 2023.07.10
     */
    public BoardResponseDTO deleteBoard(Board board, String userId) {
        if(userId.equals(board.getUser().getId())) {
            boardRepository.delete(board);

            return BoardResponseDTO.builder()
                    .id(board.getId())
                    .userId(board.getUser().getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .regTime(board.getRegTime())
                    .updateTime(board.getUpdateTime())
                    .recommend(board.getRecommend())
                    .build();
        }
        return null;
    }

    /**
     * 단일 게시글 조회, 댓글 리턴
     * - 전우진 2023.07.11
     */
    public BoardResponseDTO getBoard(Long id, String userId) {
        Board board = boardRepository.findById(id).orElse(null);

        if(board != null) { // 게시글이 존재하는 경우
            // 댓글
            List<BoardComment> comments = board.getComments();

            List<BoardCommentResponseDTO> commentDTOList = new ArrayList<>();
            Map<Long, BoardCommentResponseDTO> commentDTOHashMap = new HashMap<>();

            comments.forEach( c -> {
                BoardCommentResponseDTO boardCommentResponseDTO = new BoardCommentResponseDTO(c.getId(), c.getBoard().getId(), c.getUser().getId(), c.getContent(), c.getParent() == null ? 0 : c.getParent().getId(), c.getRegTime(), c.getUpdateTime());
                commentDTOHashMap.put(boardCommentResponseDTO.getId(), boardCommentResponseDTO);
                if(c.getParent() != null) commentDTOHashMap.get(c.getParent().getId()).getChildren().add(boardCommentResponseDTO);
                else commentDTOList.add(boardCommentResponseDTO);
            });

            return BoardResponseDTO.builder()
                    .id(board.getId())
                    .userId(board.getUser().getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .regTime(board.getRegTime())
                    .updateTime(board.getUpdateTime())
                    .recommend(board.getRecommend())
                    .isrecommend(board.getRecommends().stream().anyMatch(recommend -> recommend.getUser().getId().equals(userId)))
                    .comments(commentDTOList)
                    .build();

        } else {
            // 게시글이 존재하지 않는 경우

            return null;
        }
    }

    /**
     * 게시글 페이징 후 최근 값부터 댓글 포함 리턴
     * - 전우진 2023.07.11
     */
    public List<BoardResponseDTO> getAllBoard(int page, String userId) {

        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("id").descending());

        List<Board> boards = boardRepository.findAllBoard(pageable);
        List<BoardResponseDTO> result = new ArrayList<>();

        for (Board board : boards) {
            List<BoardCommentResponseDTO> commentDTOList = new ArrayList<>();
            Map<Long, BoardCommentResponseDTO> commentDTOHashMap = new HashMap<>();

            board.getComments().forEach( c -> {
                BoardCommentResponseDTO boardCommentResponseDTO = new BoardCommentResponseDTO(c.getId(), c.getBoard().getId(), c.getUser().getId(), c.getContent(), c.getParent() == null ? 0 : c.getParent().getId(), c.getRegTime(), c.getUpdateTime());
                commentDTOHashMap.put(boardCommentResponseDTO.getId(), boardCommentResponseDTO);
                if(c.getParent() != null) commentDTOHashMap.get(c.getParent().getId()).getChildren().add(boardCommentResponseDTO);
                else commentDTOList.add(boardCommentResponseDTO);
            });

            BoardResponseDTO boardResponseDTO = new BoardResponseDTO(
                    board.getId(),
                    board.getUser().getId(),
                    board.getTitle(),
                    board.getContent(),
                    board.getRegTime(),
                    board.getUpdateTime(),
                    board.getRecommend(),
                    board.getRecommends().stream().anyMatch(recommend -> recommend.getUser().getId().equals(userId)),
                    commentDTOList
            );

            result.add(boardResponseDTO);
        }

        return result;
    }

    public Board FindBoard(Long id){
        return boardRepository.findById(id).orElse(null);
    }
}
