package com.example.walkingmate_back.board.controller;

import com.example.walkingmate_back.board.dto.BoardCommentRequestDTO;
import com.example.walkingmate_back.board.dto.BoardCommentResponseDTO;
import com.example.walkingmate_back.board.service.BoardCommentService;
import com.example.walkingmate_back.main.entity.ResponseMessage;
import com.example.walkingmate_back.main.entity.DefaultRes;
import com.example.walkingmate_back.main.entity.StatusEnum;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *    댓글 등록, 수정, 삭제
 *
 *   @version          1.00 / 2023.07.20
 *   @author           전우진
 */

//@Controller
@RestController
@RequestMapping("/board/comments")
public class BoardCommentController {

    private final BoardCommentService boardCommentService;
    private final UserService userService;

    public BoardCommentController(BoardCommentService boardCommentService, UserService userService) {
        this.boardCommentService = boardCommentService;
        this.userService = userService;
    }

    // 댓글 작성
    @PostMapping("/save")
    public ResponseEntity<DefaultRes<BoardCommentResponseDTO>> saveComment(@RequestBody BoardCommentRequestDTO boardCommentRequestDTO){
        UserEntity user = userService.FindUser(boardCommentRequestDTO.getUserId());

        if(user == null) return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.NOT_FOUND_USER, null), HttpStatus.OK);

        BoardCommentResponseDTO boardCommentResponseDTO = boardCommentService.saveComment(boardCommentRequestDTO, user);

        if(boardCommentResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.WRITE_BOARDCOMMENT, boardCommentResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.DB_ERROR, ResponseMessage.NOT_FOUND_BOARD, null), HttpStatus.OK);
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<DefaultRes<BoardCommentResponseDTO>> updateComment(@PathVariable Long id, @RequestBody BoardCommentRequestDTO boardCommentRequestDTO) {
        BoardCommentResponseDTO boardCommentResponseDTO = boardCommentService.updateComment(id, boardCommentRequestDTO);

        if(boardCommentResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.UPDATE_BOARDCOMMENT, boardCommentResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.DB_ERROR, ResponseMessage.NOT_FOUND_BOARDCOMMENT, null), HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultRes<BoardCommentResponseDTO>> deleteComment(@PathVariable Long id) {
        BoardCommentResponseDTO boardCommentResponseDTO = boardCommentService.deleteComment(id);

        if(boardCommentResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.DELETE_BOARDCOMMENT, boardCommentResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.DB_ERROR, ResponseMessage.NOT_FOUND_BOARDCOMMENT, null), HttpStatus.OK);
    }

}
