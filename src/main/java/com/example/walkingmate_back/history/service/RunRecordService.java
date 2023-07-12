package com.example.walkingmate_back.history.service;

import com.example.walkingmate_back.board.dto.BoardResponseDTO;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RunRecordService {

    private final RunRecordRepository runRecordRepository;
    private final UserRepository userRepository;

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
