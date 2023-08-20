package com.example.walkingmate_back.history.service;

import com.example.walkingmate_back.history.dto.HomeResponseDTO;
import com.example.walkingmate_back.history.dto.RunRecordAVGDTO;
import com.example.walkingmate_back.history.dto.RunRecordRequestDTO;
import com.example.walkingmate_back.history.dto.RunRecordResponseDTO;
import com.example.walkingmate_back.history.entity.RunRecord;
import com.example.walkingmate_back.history.repository.RunRecordRepository;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.entity.UserRank;
import com.example.walkingmate_back.user.repository.UserRankRepository;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *    운동 기록 등록, 조회 - 날짜별, 금일 운동 기록 조회, 평균 운동 기록 조회, 운동 기록 수정
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.08.20
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class RunRecordService {

    private final RunRecordRepository runRecordRepository;
    private final UserRepository userRepository;
    private final UserRankRepository userRankRepository;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 사용자 확인 후 운동 기록 저장
     * - 전우진 2023.07.12
     */
    public RunRecordResponseDTO saveRun(RunRecordRequestDTO runRecordRequestDTO, String userId) throws ParseException {
        UserEntity user = userRepository.findById(userId).orElse(null);
        LocalDate now = LocalDate.now();

        if(user != null) {  // 사용자가 존재하는 경우
            RunRecord runRecord = new RunRecord(user, now, runRecordRequestDTO.getStep(), runRecordRequestDTO.getDistance(), runRecordRequestDTO.getKcal(), runRecordRequestDTO.getTime(), runRecordRequestDTO.getStartTime());
            runRecordRepository.save(runRecord);

            UserRank userRank = userRankRepository.findById(user.getId()).orElse(null);
            userRank.updateRunNum();
            userRankRepository.save(userRank);

            String date = runRecord.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            return RunRecordResponseDTO.builder()
                    .id(runRecord.getId())
                    .userId(runRecord.getUser().getId())
                    .kcal(runRecord.getKcal())
                    .date(date)
                    .step(runRecord.getStep())
                    .distance(runRecord.getDistance())
                    .time(runRecord.getTime())
                    .startTime(runRecord.getStartTime().toString())
                    .build();
        } else {
            // 사용자가 존재하지 않는 경우
            return null;
        }
    }

    /**
     * 사용자 확인 후 날짜별 운동 기록 조회
     * - 전우진 2023.07.12
     */
    public List<RunRecordResponseDTO> getDateRun(String id, String date) {
        UserEntity user = userRepository.findById(id).orElse(null);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate lc = LocalDate.parse(date, formatter);

        List<RunRecord> runRecords = runRecordRepository.findByUserIdWithDate(user.getId(), lc);
        List<RunRecordResponseDTO> result = new ArrayList<>();

        for (RunRecord runRecord : runRecords) {
            RunRecordResponseDTO runRecordResponseDTO = new RunRecordResponseDTO(
                runRecord.getId(),
                runRecord.getUser().getId(),
                runRecord.getKcal(),
                runRecord.getDate().toString(),
                runRecord.getStep(),
                runRecord.getDistance(),
                runRecord.getTime(),
                runRecord.getStartTime().toString(),
                runRecord.getEndTime()
            );
            result.add(runRecordResponseDTO);
        }
        return result;
    }

    /**
     * 사용자 확인 후 전체 운동 기록 조회
     * - 전우진 2023.07.14
     */
    public List<RunRecordResponseDTO> getAllRun(String id) {
        UserEntity user = userRepository.findById(id).orElse(null);

        List<RunRecord> runRecords = runRecordRepository.findByUserIdOrderByDateDesc(user.getId());
        List<RunRecordResponseDTO> result = new ArrayList<>();

        for (RunRecord runRecord : runRecords) {
            RunRecordResponseDTO runRecordResponseDTO = new RunRecordResponseDTO(
                    runRecord.getId(),
                    runRecord.getUser().getId(),
                    runRecord.getKcal(),
                    runRecord.getDate().toString(),
                    runRecord.getStep(),
                    runRecord.getDistance(),
                    runRecord.getTime(),
                    runRecord.getStartTime().toString(),
                    runRecord.getEndTime()
            );
            result.add(runRecordResponseDTO);
        }

        return result;
    }

    /**
     * 사용자 확인 후 금일 운동 기록 조회
     * - 전우진 2023.07.23
     */
    public HomeResponseDTO getDateRunHome(String userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        LocalDate lc = LocalDate.now();

        List<RunRecord> runRecords = runRecordRepository.findByUserIdWithDate(user.getId(), lc);
        int totalStep = 0;
        double totalDis = 0;
        long totalKcal = 0;

        for (RunRecord runRecord : runRecords) {
            totalStep += runRecord.getStep();
            totalDis += runRecord.getDistance();
            totalKcal += runRecord.getKcal();
        }

        return HomeResponseDTO.builder()
                .step(totalStep)
                .distance(totalDis)
                .kcal(totalKcal)
                .build();
    }

    /**
     * 사용자 확인 후 평균 운동 기록 조회
     * - 전우진 2023.07.23
     */
    public RunRecordAVGDTO getRunAVG(String userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);

        List<RunRecord> runRecords = runRecordRepository.findByUserId(user.getId());
        int totalStep = 0, avgStep = 0;
        double totalDis = 0, avgDis = 0;
        int num = 0;
        for (RunRecord runRecord : runRecords) {
            totalStep += runRecord.getStep();
            totalDis += runRecord.getDistance();
        }
        num = runRecords.size();
        avgStep = totalStep / num;
        avgDis = Math.round((totalDis / num) * 100) / 100.0;

        return RunRecordAVGDTO.builder()
                .num(num)
                .step(avgStep)
                .distance(avgDis)
                .build();
    }

    /**
     * 운동 기록 수정
     * - 전우진 2023.07.24
     */
    public RunRecordResponseDTO modifyRun(Long id, RunRecordRequestDTO runRecordRequestDTO) {
       RunRecord runRecord = runRecordRepository.findById(id).orElse(null);

       if(runRecord == null) return null;

       runRecord.update(runRecordRequestDTO);
       runRecordRepository.save(runRecord);

       String date = runRecord.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
       return RunRecordResponseDTO.builder()
               .id(runRecord.getId())
               .userId(runRecord.getUser().getId())
               .kcal(runRecord.getKcal())
               .date( date)
               .step(runRecord.getStep())
               .distance(runRecord.getDistance())
               .time(runRecord.getTime())
               .startTime(runRecord.getStartTime().toString())
               .endTime(runRecord.getEndTime())
               .build();
    }
}
