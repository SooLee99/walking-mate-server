package com.example.walkingmate_back.history.service;

import com.example.walkingmate_back.history.dto.RunRecordRequestDTO;
import com.example.walkingmate_back.history.dto.RunRecordResponseDTO;
import com.example.walkingmate_back.history.entity.RunRecord;
import com.example.walkingmate_back.history.repository.RunRecordRepository;
import com.example.walkingmate_back.main.entity.Message;
import com.example.walkingmate_back.main.entity.StatusEnum;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *    운동 기록 등록, 조회 - 날짜별
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.07.12
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class RunRecordService {

    private final RunRecordRepository runRecordRepository;
    private final UserRepository userRepository;

    /**
     * 사용자 확인 후 운동 기록 저장
     * - 전우진 2023.07.12
     */
    public ResponseEntity<Message> saveRun(RunRecordRequestDTO runRecordRequestDTO) throws ParseException {
        UserEntity user = userRepository.findById(runRecordRequestDTO.getUserId()).orElse(null);
        LocalDate now = LocalDate.now();

        if(user != null) {  // 사용자가 존재하는 경우
            RunRecord runRecord = new RunRecord(user, now, runRecordRequestDTO.getStep(), runRecordRequestDTO.getDistance());
            runRecordRepository.save(runRecord);

            Message message = new Message();
            message.setStatus(StatusEnum.OK);
            message.setMessage("성공 코드");
            message.setData("운동 기록 저장 성공");

            return ResponseEntity.ok().body(message);
        } else {
            // 사용자가 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 사용자 확인 후 날짜별 운동 기록 조회
     * - 전우진 2023.07.12
     */
    public ResponseEntity<Message> getDateRun(String id, String date) {
        UserEntity user = userRepository.findById(id).orElse(null);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate lc = LocalDate.parse(date, formatter);

        List<RunRecord> runRecords = runRecordRepository.findByUserIdWithDate(user.getId(), lc);
        List<RunRecordResponseDTO> result = new ArrayList<>();

        for (RunRecord runRecord : runRecords) {
            RunRecordResponseDTO runRecordResponseDTO = new RunRecordResponseDTO(
                runRecord.getUser().getId(),
                runRecord.getDate().toString(),
                runRecord.getStep(),
                runRecord.getDistance(),
                runRecord.getRegTime(),
                runRecord.getUpdateTime()
            );
            result.add(runRecordResponseDTO);
        }

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData(result);

        return ResponseEntity.ok().body(message);
    }

    /**
     * 사용자 확인 후 운동 기록 조회
     * - 전우진 2023.07.14
     */
    public ResponseEntity<Message> getAllRun(String id) {
        UserEntity user = userRepository.findById(id).orElse(null);

        List<RunRecord> runRecords = runRecordRepository.findByUserId(user.getId());
        List<RunRecordResponseDTO> result = new ArrayList<>();

        for (RunRecord runRecord : runRecords) {
            RunRecordResponseDTO runRecordResponseDTO = new RunRecordResponseDTO(
                    runRecord.getUser().getId(),
                    runRecord.getDate().toString(),
                    runRecord.getStep(),
                    runRecord.getDistance(),
                    runRecord.getRegTime(),
                    runRecord.getUpdateTime()
            );
            result.add(runRecordResponseDTO);
        }

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData(result);

        return ResponseEntity.ok().body(message);
    }
}
