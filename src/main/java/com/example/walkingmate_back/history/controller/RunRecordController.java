package com.example.walkingmate_back.history.controller;

import com.example.walkingmate_back.history.dto.RunRecordRequestDTO;
import com.example.walkingmate_back.history.dto.RunRecordResponseDTO;
import com.example.walkingmate_back.history.service.RunRecordService;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

/**
 *    운동 기록 등록, 조회 - 날짜별
 *
 *   @version          1.00 / 2023.07.12
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
    public int saveRun(@RequestBody RunRecordRequestDTO runRecordRequestDTO) throws ParseException {
        return runRecordService.saveRun(runRecordRequestDTO);
    }

    // 운동 기록 조회 - 날짜별
    @GetMapping({"/list/{date}"})
    public List<RunRecordResponseDTO> listDateRun(@PathVariable String date) {
        String nickName = "aaa";
        Optional<UserEntity> user = userRepository.findById(nickName);

        return runRecordService.getDateRun(user.get().getId(), date);
    }

    // 운동 기록 조회
    @GetMapping({"/list"})
    public List<RunRecordResponseDTO> listAllRun() {
        String nickName = "aaa";
        Optional<UserEntity> user = userRepository.findById(nickName);

        return runRecordService.getAllRun(user.get().getId());
    }
}
