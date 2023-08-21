package com.example.walkingmate_back.team.dto;

import com.example.walkingmate_back.history.dto.RunRecordResponseDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class TeamMemberSearchResponseDTO {

    private String userId;

    private double totalKcal;  // 칼로리

    private int totalStep;  // 걸음 수

    private double totalDistance;  // 거리

    private long totalTime; // 시간

    private List<RunRecordResponseDTO> runRecordResponseDTOList;  // 운동 기록

    @Builder
    public TeamMemberSearchResponseDTO(String userId, double totalKcal, int totalStep, double totalDistance, long totalTime, List<RunRecordResponseDTO> runRecordResponseDTOList) {
        this.userId=userId;
        this.totalKcal = totalKcal;
        this.totalStep = totalStep;
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;
        this.runRecordResponseDTOList=runRecordResponseDTOList;
    }
}
