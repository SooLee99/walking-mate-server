package com.example.walkingmate_back.board.controller;

import com.example.walkingmate_back.board.dto.*;
import com.example.walkingmate_back.board.entity.BoardComment;
import com.example.walkingmate_back.board.service.BoardCommentService;
import com.example.walkingmate_back.main.response.DefaultRes;
import com.example.walkingmate_back.main.response.ResponseMessage;
import com.example.walkingmate_back.main.response.StatusEnum;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 *    댓글 등록, 수정, 삭제 - 대댓글 포함
 *
 *   @version          1.00 / 2023.08.07
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

    // 댓글 저장
    @PostMapping("/save/{id}")
    public ResponseEntity<DefaultRes<BoardCommentResponseDTO>> saveComment(@PathVariable Long id, @RequestBody BoardCommentRequestDTO boardCommentRequestDTO, Authentication authentication) {
        UserEntity user = userService.FindUser(authentication.getName());

        if(user == null) return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER, null), HttpStatus.OK);

        BoardCommentResponseDTO boardCommentResponseDTO = boardCommentService.saveCommemt(boardCommentRequestDTO, user, id);

        if(boardCommentResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.WRITE_BOARDCOMMENT, boardCommentResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_BOARD, null), HttpStatus.OK);
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<DefaultRes<BoardCommentResponseDTO>> updateComment(@PathVariable Long id, @RequestBody BoardCommentUpdateDTO boardCommentUpdateDTO, Authentication authentication) {
        BoardComment boardComment = boardCommentService.FindBoardComment(id);
        if(boardComment == null) return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_BOARDCOMMENT, null), HttpStatus.OK);

        BoardCommentResponseDTO boardCommentResponseDTO = boardCommentService.updateComment(boardComment, boardCommentUpdateDTO, authentication.getName());

        if(boardCommentResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.UPDATE_BOARDCOMMENT, boardCommentResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER, null), HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultRes<BoardCommentResponseDTO>> deleteComment(@PathVariable Long id, Authentication authentication) {
        BoardComment boardComment = boardCommentService.FindBoardComment(id);
        if(boardComment == null) return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_BOARDCOMMENT, null), HttpStatus.OK);

        BoardCommentResponseDTO boardCommentResponseDTO = boardCommentService.deleteComment(boardComment, authentication.getName());

        if(boardCommentResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.DELETE_BOARDCOMMENT, boardCommentResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_BOARDCOMMENT, null), HttpStatus.OK);
    }
}
