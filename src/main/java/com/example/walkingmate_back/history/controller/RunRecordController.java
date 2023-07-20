package com.example.walkingmate_back.history.controller;

import com.example.walkingmate_back.history.dto.RunRecordRequestDTO;
import com.example.walkingmate_back.history.dto.RunRecordResponseDTO;
import com.example.walkingmate_back.history.service.RunRecordService;
import com.example.walkingmate_back.main.entity.DefaultRes;
import com.example.walkingmate_back.main.entity.ResponseMessage;
import com.example.walkingmate_back.main.entity.StatusEnum;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.List;

/**
 *    운동 기록 등록, 조회 - 날짜별
 *
 *   @version          1.00 / 2023.07.20
 *   @author           전우진
 */

//@Controller
@RestController
@RequestMapping("/run")
public class RunRecordController {

    private final RunRecordService runRecordService;
    private final UserRepository userRepository;

    public RunRecordController(RunRecordService runRecordService, UserRepository userRepository) {
        this.runRecordService = runRecordService;
        this.userRepository = userRepository;
    }

    // 운동 기록 추가
    @PostMapping("/record")
    public ResponseEntity<DefaultRes<RunRecordResponseDTO>> saveRun(@RequestBody RunRecordRequestDTO runRecordRequestDTO) throws ParseException {
        RunRecordResponseDTO runRecordResponseDTO = runRecordService.saveRun(runRecordRequestDTO);

        if(runRecordResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.WRITE_RUNRECORD, runRecordResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.DB_ERROR, ResponseMessage.NOT_FOUND_USER, null), HttpStatus.OK);
    }

    // 운동 기록 조회 - 날짜별
    @GetMapping({"/list/{date}"})
    public ResponseEntity<DefaultRes<List<RunRecordResponseDTO>>> listDateRun(@PathVariable String date) {
        String nickName = "aaa";
        UserEntity user = userRepository.findById(nickName).orElse(null);

        List<RunRecordResponseDTO> runRecordResponseDTO = runRecordService.getDateRun(user.getId(), date);

        if(runRecordResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_SUCCESS, runRecordResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.DB_ERROR, ResponseMessage.NOT_FOUND_RUNRECORD, null), HttpStatus.OK);
    }

    // 운동 기록 조회
    @GetMapping({"/list"})
    public ResponseEntity<DefaultRes<List<RunRecordResponseDTO>>> listAllRun() {
        String nickName = "aaa";
        UserEntity user = userRepository.findById(nickName).orElse(null);

        List<RunRecordResponseDTO> runRecordResponseDTO = runRecordService.getAllRun(user.getId());


        if(runRecordResponseDTO != null)
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.OK, ResponseMessage.READ_SUCCESS, runRecordResponseDTO), HttpStatus.OK);
        else
            return new ResponseEntity<>(DefaultRes.res(StatusEnum.DB_ERROR, ResponseMessage.NOT_FOUND_RUNRECORD, null), HttpStatus.OK);
    }
}
