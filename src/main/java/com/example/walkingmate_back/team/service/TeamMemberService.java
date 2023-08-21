package com.example.walkingmate_back.team.service;

import com.example.walkingmate_back.history.dto.RunRecordResponseDTO;
import com.example.walkingmate_back.history.entity.RunRecord;
import com.example.walkingmate_back.history.repository.RunRecordRepository;
import com.example.walkingmate_back.team.dto.TeamMemberResponseDTO;
import com.example.walkingmate_back.team.dto.TeamMemberSearchResponseDTO;
import com.example.walkingmate_back.team.entity.Team;
import com.example.walkingmate_back.team.entity.TeamMember;
import com.example.walkingmate_back.team.repository.TeamMemberRepository;
import com.example.walkingmate_back.team.repository.TeamRepository;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 *    멤버 가입, 나가기(삭제), 검색
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.08.21
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final TeamRepository teamRepository;
    private final RunRecordRepository runRecordRepository;
    private final UserRepository userRepository;

    /**
     * 사용자, 팀 여부, 팀 확인 후 멤버 가입
     * - 전우진 2023.07.13
     */
    public TeamMemberResponseDTO saveMember(Long teamId, UserEntity user) {
        Team team = teamRepository.findById(teamId).orElse(null);
        if(team.getTeamMembers().size() != team.getPeopleNum()) {
            TeamMember teamMember = new TeamMember(user, team, false);
            teamMemberRepository.save(teamMember);

            return TeamMemberResponseDTO.builder()
                    .userId(teamMember.getUser().getId())
                    .teamId(teamMember.getTeam().getId())
                    .teamLeader(teamMember.isTeamLeader())
                    .build();
        } else {
            // 팀 인원 초과
            return null;
        }
    }

    /**
     * 팀 확인 후 멤버 나가기(삭제)
     * - 전우진 2023.07.13
     */
    public TeamMemberResponseDTO deleteTeamMember(Long teamId, String userId) {
        Team team = teamRepository.findById(teamId).orElse(null);

        if(team == null) {
            // 팀이 존재하지 않을 경우
            return null;
        }

        TeamMember teamMember = teamMemberRepository.findByUserId(userId);
        teamMemberRepository.delete(teamMember);

        return TeamMemberResponseDTO.builder()
                .userId(teamMember.getUser().getId())
                .teamId(teamMember.getTeam().getId())
                .teamLeader(teamMember.isTeamLeader())
                .build();
    }

    public TeamMember FindTeam(String userId){
        return teamMemberRepository.findByUserId(userId);
    }

    /**
     * 팀원 검색 조회
     * - 전우진 2023.08.21
     */
    public TeamMemberSearchResponseDTO searchTeamMember(String userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);

        if(user != null) {  // 사용자가 존재하는 경우
            List<RunRecord> runRecords = runRecordRepository.findByUserIdOrderByDateDesc(userId);
            List<RunRecordResponseDTO> runRecordResponseDTOList = new ArrayList<>();

            int totalStep = 0;
            double totalDis = 0;
            double totalKcal = 0;
            long totalTime = 0;

            for(RunRecord runRecord : runRecords) {
                totalStep += runRecord.getStep();
                totalDis += runRecord.getDistance();
                totalKcal += runRecord.getKcal();
                totalTime += runRecord.getTime();

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
                runRecordResponseDTOList.add(runRecordResponseDTO);
            }

            return TeamMemberSearchResponseDTO.builder()
                    .userId(user.getId())
                    .totalStep(totalStep)
                    .totalDistance(totalDis)
                    .totalKcal(totalKcal)
                    .totalTime(totalTime)
                    .runRecordResponseDTOList(runRecordResponseDTOList)
                    .build();

        } else {  // 사용자가 존재하지 않는 경우
            return null;
        }
    }
}
