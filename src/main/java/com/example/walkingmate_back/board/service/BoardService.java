package com.example.walkingmate_back.board.service;

import com.example.walkingmate_back.board.dto.BoardRequestDTO;
import com.example.walkingmate_back.board.dto.BoardResponseDTO;
import com.example.walkingmate_back.board.dto.BoardUpdateDTO;
import com.example.walkingmate_back.board.entity.Board;
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

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

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

    public int deleteBoard(Long id) {
        Board board = boardRepository.findById(id).orElse(null);

        if (board == null) {
            return -1;
        }
        boardRepository.delete(board);
        return 1;
    }

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

    public Optional<BoardResponseDTO> getBoard(Long id) {
        Optional<Board> result = boardRepository.findById(id);

        if(result.isPresent()) {
            Board board = result.get();

            return Optional.of(new BoardResponseDTO(
                    board.getId(),
                    board.getUser().getId(),
                    board.getTitle(),
                    board.getContent(),
                    board.getRegTime(),
                    board.getUpdateTime()
                    ));
        } else {
            return Optional.empty();
        }
    }

    public List<BoardResponseDTO> getAllBoard(int page) {

        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("id").descending());

        List<Board> boards = boardRepository.findAllBoard(pageable);
        List<BoardResponseDTO> result = new ArrayList<>();

        for (Board board : boards) {
            BoardResponseDTO boardResponseDTO = new BoardResponseDTO(
                    board.getId(),
                    board.getUser().getId(),
                    board.getTitle(),
                    board.getContent(),
                    board.getRegTime(),
                    board.getUpdateTime()
            );

            result.add(boardResponseDTO);
        }
        return result;
    }
}
