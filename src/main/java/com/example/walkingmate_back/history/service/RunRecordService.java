package com.example.walkingmate_back.history.service;

import com.example.walkingmate_back.history.dto.RunRecordRequestDTO;
import com.example.walkingmate_back.history.dto.RunRecordResponseDTO;
import com.example.walkingmate_back.history.entity.RunRecord;
import com.example.walkingmate_back.history.repository.RunRecordRepository;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public int saveRun(RunRecordRequestDTO runRecordRequestDTO) throws ParseException {
        Optional<UserEntity> user = userRepository.findById(runRecordRequestDTO.getUserId());
        LocalDate now = LocalDate.now();

        if(user != null) {
            RunRecord runRecord = new RunRecord(user.get(), now, runRecordRequestDTO.getStep(), runRecordRequestDTO.getDistance());
            runRecordRepository.save(runRecord);
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 사용자 확인 후 날짜별 운동 기록 조회
     * - 전우진 2023.07.12
     */
    public List<RunRecordResponseDTO> getDateRun(String id, String date) {
        Optional<UserEntity> user = userRepository.findById(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate lc = LocalDate.parse(date, formatter);

        List<RunRecord> runRecords = runRecordRepository.findByUserIdWithDate(user.get().getId(), lc);
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
        return result;
    }
}
