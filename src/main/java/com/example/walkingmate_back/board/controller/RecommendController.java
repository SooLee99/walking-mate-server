package com.example.walkingmate_back.board.controller;

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
 *    좋아요 저장
 *
 *   @version          1.00 / 2023.08.04
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

    @PostMapping("/save/{id}")
    public ResponseEntity<DefaultRes<BoardResponseDTO>> saveRecommend(@PathVariable Long id, Authentication authentication) {

        BoardResponseDTO boardResponseDTO = recommendService.saveRecommend(id, authentication.getName());

        if(boardResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.WRITE_RECOMMEND, boardResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER, null), HttpStatus.OK);
    }

//    @PostMapping("/save/{id}")
//    public ResponseEntity<DefaultRes<BoardResponseDTO>> saveRecommend(@PathVariable Long id) {
//
//        String userId = "aaa";
//
//        BoardResponseDTO boardResponseDTO = recommendService.saveRecommend(id, userId);
//
//        if(boardResponseDTO != null)
//            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.WRITE_RECOMMEND, boardResponseDTO), HttpStatus.OK);
//        else
//            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER, null), HttpStatus.OK);
//    }
}
