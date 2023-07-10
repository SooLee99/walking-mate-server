package com.example.walkingmate_back.board.controller;

import com.example.walkingmate_back.board.dto.BoardRequestDTO;
import com.example.walkingmate_back.board.dto.BoardResponseDTO;
import com.example.walkingmate_back.board.dto.BoardUpdateDTO;
import com.example.walkingmate_back.board.service.BoardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@Controller
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;	   
    }

    // 게시글 작성
    @PostMapping("/save")
    public int saveBoard(@RequestBody BoardRequestDTO boardRequestDTO){
        return boardService.saveBoard(boardRequestDTO);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public int modifyBoard(@PathVariable Long id, @RequestBody BoardUpdateDTO boardUpdateDTO) {
        return boardService.updateBoard(id, boardUpdateDTO);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public int deleteBoard(@PathVariable Long id) {
        return boardService.deleteBoard(id);
    }

    // 단일 게시글 조회
    @GetMapping("/{id}")
    public Optional<BoardResponseDTO> SpecificationBoard(@PathVariable Long id) {

        return boardService.getBoard(id);
    }

    // 게시글 전체 조회
    @GetMapping({"/list", "/list/{page}"})
    public List<BoardResponseDTO> listBoard(@PathVariable Optional<Integer> page) {
        int pageNumber = page.orElse(1);

        return boardService.getAllBoard(pageNumber);
    }
}
