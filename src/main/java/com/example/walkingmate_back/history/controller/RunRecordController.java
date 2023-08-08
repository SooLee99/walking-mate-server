package com.example.walkingmate_back.history.controller;

import com.example.walkingmate_back.history.dto.HomeResponseDTO;
import com.example.walkingmate_back.history.dto.RunRecordAVGDTO;
import com.example.walkingmate_back.history.dto.RunRecordRequestDTO;
import com.example.walkingmate_back.history.dto.RunRecordResponseDTO;
import com.example.walkingmate_back.history.service.RunRecordService;
import com.example.walkingmate_back.main.response.DefaultRes;
import com.example.walkingmate_back.main.response.ResponseMessage;
import com.example.walkingmate_back.main.response.StatusEnum;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.List;

/**
 *    운동 기록 등록, 조회 - 날짜별, 금일 운동 기록 조회, 평균 운동 기록 조회
 *
 *   @version          1.00 / 2023.07.24
 *   @author           전우진
 */

//@Controller
@RestController
@RequestMapping("/run")
public class RunRecordController {

    private final RunRecordService runRecordService;
    private final UserService userService;

    public RunRecordController(RunRecordService runRecordService, UserService userService) {
        this.runRecordService = runRecordService;
        this.userService = userService;
    }

    // 운동 기록 추가
    @PostMapping("/record")
    public ResponseEntity<DefaultRes<RunRecordResponseDTO>> saveRun(@RequestBody RunRecordRequestDTO runRecordRequestDTO, Authentication authentication) throws ParseException {
        RunRecordResponseDTO runRecordResponseDTO = runRecordService.saveRun(runRecordRequestDTO, authentication.getName());

        if(runRecordResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.WRITE_RUNRECORD, runRecordResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER, null), HttpStatus.OK);
    }

    // 운동 기록 조회 - 날짜별
    @GetMapping({"/list/{date}"})
    public ResponseEntity<DefaultRes<List<RunRecordResponseDTO>>> listDateRun(@PathVariable String date, Authentication authentication) {
        UserEntity user = userService.FindUser(authentication.getName());

        List<RunRecordResponseDTO> runRecordResponseDTO = runRecordService.getDateRun(user.getId(), date);

        if(runRecordResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_SUCCESS, runRecordResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_RUNRECORD, null), HttpStatus.OK);
    }

    // 전체 운동 기록 조회
    @GetMapping({"/list"})
    public ResponseEntity<DefaultRes<List<RunRecordResponseDTO>>> listAllRun(Authentication authentication) {
        UserEntity user = userService.FindUser(authentication.getName());

        List<RunRecordResponseDTO> runRecordResponseDTO = runRecordService.getAllRun(user.getId());

        if(runRecordResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_SUCCESS, runRecordResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_RUNRECORD, null), HttpStatus.OK);
    }

    // 금일 운동 기록 조회
    @GetMapping({"/list/home"})
    public ResponseEntity<DefaultRes<HomeResponseDTO>> getDateRunHome(Authentication authentication) {
        UserEntity user = userService.FindUser(authentication.getName());

        HomeResponseDTO homeResponseDTO = runRecordService.getDateRunHome(user.getId());

        if(homeResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_SUCCESS, homeResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_RUNRECORD, null), HttpStatus.OK);
    }

    // 평균 운동 기록 조회
    @GetMapping({"/avgList"})
    public ResponseEntity<DefaultRes<RunRecordAVGDTO>> getRunAVG(Authentication authentication) {
        UserEntity user = userService.FindUser(authentication.getName());

        RunRecordAVGDTO runRecordAVGDTO = runRecordService.getRunAVG(user.getId());

        if(runRecordAVGDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_SUCCESS, runRecordAVGDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_RUNRECORD, null), HttpStatus.OK);
    }

    // 운동 기록 수정
    @PutMapping("/record/{id}")
    public ResponseEntity<DefaultRes<RunRecordResponseDTO>> modifyRun(@PathVariable Long id, @RequestBody RunRecordRequestDTO runRecordRequestDTO) throws ParseException {
        RunRecordResponseDTO runRecordResponseDTO = runRecordService.modifyRun(id, runRecordRequestDTO);

        if(runRecordResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.WRITE_RUNRECORD, runRecordResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.BAD_REQUEST, ResponseMessage.NOT_FOUND_RUNRECORD, null), HttpStatus.OK);
    }
}
