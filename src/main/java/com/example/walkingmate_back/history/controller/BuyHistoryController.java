package com.example.walkingmate_back.history.controller;

import com.example.walkingmate_back.history.dto.BuyHistoryRequestDTO;
import com.example.walkingmate_back.history.dto.BuyHistoryResponseDTO;
import com.example.walkingmate_back.history.dto.CoinRequestDTO;
import com.example.walkingmate_back.history.service.BuyHistoryService;
import com.example.walkingmate_back.main.response.DefaultRes;
import com.example.walkingmate_back.main.response.ResponseMessage;
import com.example.walkingmate_back.main.response.StatusEnum;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *    코인 구매, 구매내역 조회, 코인 사용
 *
 *   @version          1.00 / 2023.07.24
 *   @author           전우진
 */

//@Controller
@RestController
@RequestMapping("/buyHistory")
public class BuyHistoryController {

    private final BuyHistoryService buyHistoryService;
    private final UserService userService;

    public BuyHistoryController(BuyHistoryService buyHistoryService, UserService userService) {
        this.buyHistoryService = buyHistoryService;
        this.userService = userService;
    }

    // 코인 구매
    @PostMapping("/save")
    public ResponseEntity<DefaultRes<BuyHistoryResponseDTO>> saveBuyHistory(@RequestBody BuyHistoryRequestDTO buyHistoryRequestDTO, Authentication authentication) {
        BuyHistoryResponseDTO buyHistoryResponseDTO = buyHistoryService.saveBuyHistory(buyHistoryRequestDTO, authentication.getName());

        if(buyHistoryResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.WRITE_BUYHISTORY, buyHistoryResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER, null), HttpStatus.OK);
    }

    // 코인 구매내역 조회
    @GetMapping("/list")
    public ResponseEntity<DefaultRes<List<BuyHistoryResponseDTO>>> listBuyHistory(Authentication authentication) {
        UserEntity user = userService.FindUser(authentication.getName());

        List<BuyHistoryResponseDTO> buyHistoryResponseDTO = buyHistoryService.getBuyHistory(user.getId());

        if(buyHistoryResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_SUCCESS, buyHistoryResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_BUYHISTORY, null), HttpStatus.OK);
    }

    // 코인 사용
    @PutMapping("/coin")
    public ResponseEntity<DefaultRes<BuyHistoryResponseDTO>> modifyBuyHistory(@RequestBody CoinRequestDTO coinRequestDTO, Authentication authentication) {
        UserEntity user = userService.FindUser(authentication.getName());
        BuyHistoryResponseDTO buyHistoryResponseDTO = buyHistoryService.modifyBuyHistory(coinRequestDTO, user);

        if(buyHistoryResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.WRITE_BUYHISTORY, buyHistoryResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER, null), HttpStatus.OK);
    }
}
