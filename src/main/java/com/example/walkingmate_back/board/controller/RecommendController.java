package com.example.walkingmate_back.board.controller;

import com.example.walkingmate_back.board.dto.BoardCommentResponseDTO;
import com.example.walkingmate_back.board.dto.BoardResponseDTO;
import com.example.walkingmate_back.board.service.RecommendService;
import com.example.walkingmate_back.main.response.DefaultRes;
import com.example.walkingmate_back.main.response.ResponseMessage;
import com.example.walkingmate_back.main.response.StatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 *    게시글 좋아요, 댓글 좋아요 저장
 *
 *   @version          1.00 / 2023.08.30
 *   @author           전우진
 */

//@Controller
@RestController
@RequestMapping("/board/recommend")
public class RecommendController {

    private final RecommendService recommendService;

    public RecommendController(RecommendService recommendService) {
        this.recommendService = recommendService;
    }

    // 게시글 좋아요 저장
    @PostMapping("/save/{id}")
    public ResponseEntity<DefaultRes<BoardResponseDTO>> saveRecommend(@PathVariable Long id, Authentication authentication) {

        BoardResponseDTO boardResponseDTO = recommendService.saveRecommend(id, authentication.getName());

        if(boardResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.WRITE_RECOMMEND, boardResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_BOARD, null), HttpStatus.OK);
    }

    // 댓글 좋아요 저장
    @PostMapping("/comment/save/{id}")
    public ResponseEntity<DefaultRes<BoardCommentResponseDTO>> saveRecommendComment(@PathVariable Long id, Authentication authentication) {

        BoardCommentResponseDTO boardCommentResponseDTO = recommendService.saveRecommendComment(id, authentication.getName());

        if(boardCommentResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.WRITE_RECOMMEND, boardCommentResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_BOARDCOMMENT, null), HttpStatus.OK);
    }

}
