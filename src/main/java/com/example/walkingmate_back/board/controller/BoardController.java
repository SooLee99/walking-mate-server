package com.example.walkingmate_back.board.controller;

import com.example.walkingmate_back.board.dto.BoardRequestDTO;
import com.example.walkingmate_back.board.dto.BoardUpdateDTO;
import com.example.walkingmate_back.board.service.BoardService;
import com.example.walkingmate_back.main.entity.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

/**
 *    게시글 등록, 수정, 삭제, 단일 조회, 전체 조회
 *
 *   @version          1.00 / 2023.07.18
 *   @author           전우진
 */

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
    public ResponseEntity<Message> saveBoard(@RequestBody BoardRequestDTO boardRequestDTO){
        return boardService.saveBoard(boardRequestDTO);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateBoard(@PathVariable Long id, @RequestBody BoardUpdateDTO boardUpdateDTO) {
        return boardService.updateBoard(id, boardUpdateDTO);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteBoard(@PathVariable Long id) {
        return boardService.deleteBoard(id);
    }

    // 단일 게시글 조회 - 댓글 포함
    @GetMapping("/{id}")
    public ResponseEntity<Message> SpecificationBoard(@PathVariable Long id) {

        return boardService.getBoard(id);
    }

    // 게시글 전체 조회 - 댓글 포함
    @GetMapping({"/list", "/list/{page}"})
    public ResponseEntity<Message> listBoard(@PathVariable Optional<Integer> page) {
        int pageNumber = page.orElse(1);

        return boardService.getAllBoard(pageNumber);
    }
}
