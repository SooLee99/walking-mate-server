package com.example.walkingmate_back.board.controller;

import com.example.walkingmate_back.board.dto.BoardRequestDTO;
import com.example.walkingmate_back.board.dto.BoardResponseDTO;
import com.example.walkingmate_back.board.dto.BoardUpdateDTO;
import com.example.walkingmate_back.board.service.BoardService;
import com.example.walkingmate_back.main.response.ResponseMessage;
import com.example.walkingmate_back.main.response.DefaultRes;
import com.example.walkingmate_back.main.response.StatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 *    게시글 등록, 수정, 삭제, 단일 조회, 전체 조회
 *
 *   @version          1.00 / 2023.07.21
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
    public ResponseEntity<DefaultRes<BoardResponseDTO>> saveBoard(@RequestBody BoardRequestDTO boardRequestDTO){
        BoardResponseDTO boardResponseDTO = boardService.saveBoard(boardRequestDTO);

        if(boardResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.WRITE_BOARD, boardResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER, null), HttpStatus.OK);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<DefaultRes<BoardResponseDTO>> updateBoard(@PathVariable Long id, @RequestBody BoardUpdateDTO boardUpdateDTO) {
        BoardResponseDTO boardResponseDTO = boardService.updateBoard(id, boardUpdateDTO);

        if(boardResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.UPDATE_BOARD, boardResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_BOARD, null), HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultRes<BoardResponseDTO>> deleteBoard(@PathVariable Long id) {
        BoardResponseDTO boardResponseDTO = boardService.deleteBoard(id);

        if(boardResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.DELETE_BOARD, boardResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_BOARD, null), HttpStatus.OK);
    }

    // 단일 게시글 조회 - 댓글 포함
    @GetMapping("/{id}")
    public ResponseEntity<DefaultRes<BoardResponseDTO>> SpecificationBoard(@PathVariable Long id) {
        BoardResponseDTO boardResponseDTO = boardService.getBoard(id);

        if(boardResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_SUCCESS, boardResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_BOARD, null), HttpStatus.OK);
    }

    // 게시글 전체 조회 - 댓글 포함
    @GetMapping({"/list", "/list/{page}"})
    public ResponseEntity<DefaultRes<List<BoardResponseDTO>>> listBoard(@PathVariable Optional<Integer> page) {
        int pageNumber = page.orElse(1);

        List<BoardResponseDTO> boardResponseDTO = boardService.getAllBoard(pageNumber);

        if(boardResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_SUCCESS, boardResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_BOARD, null), HttpStatus.OK);
    }
}
