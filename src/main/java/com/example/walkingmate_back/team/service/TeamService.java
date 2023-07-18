package com.example.walkingmate_back.team.service;

import com.example.walkingmate_back.main.entity.Message;
import com.example.walkingmate_back.main.entity.StatusEnum;
import com.example.walkingmate_back.team.dto.TeamMemberResponseDTO;
import com.example.walkingmate_back.team.dto.TeamRankResponseDTO;
import com.example.walkingmate_back.team.dto.TeamRequestDTO;
import com.example.walkingmate_back.team.dto.TeamResponseDTO;
import com.example.walkingmate_back.team.entity.Team;
import com.example.walkingmate_back.team.entity.TeamMember;
import com.example.walkingmate_back.team.entity.TeamRank;
import com.example.walkingmate_back.team.repository.TeamMemberRepository;
import com.example.walkingmate_back.team.repository.TeamRepository;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *    팀 생성, 삭제, 단일 조회, 전체 조회, 가입된 팀 정보 조회
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.07.18
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMemberRepository teamMemberRepository;

    /**
     * 사용자 확인 후 팀 생성
     * - 전우진 2023.07.13
     */
    public ResponseEntity<Message> saveTeam(TeamRequestDTO teamRequestDTO, String userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);

        if(user != null) {  // 사용자가 존재하는 경우
            if(user.getTeam() == null) {  // 기존 팀이 없는 경우
                Team team = new Team(teamRequestDTO.getName(), teamRequestDTO.getPeopleNum(), "모집");
                teamRepository.save(team);

                // 팀 가입 - 리더
                TeamMember teamMember = new TeamMember(user, team, true);
                teamMemberRepository.save(teamMember);

                // 사용자 팀 아이디 추가

                Message message = new Message();
                message.setStatus(StatusEnum.OK);
                message.setMessage("성공 코드");
                message.setData("팀 생성 성공");

                return ResponseEntity.ok().body(message);
            } else {
                // 기존 팀이 이미 있으므로 생성 불가
                return ResponseEntity.notFound().build();
            }
        } else {
            // 사용자가 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 팀 확인 후 팀 삭제
     * - 전우진 2023.07.13
     */
    public ResponseEntity<Message> deleteTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElse(null);

        if(team == null) {
            // 팀이 존재하지 않는 경우
            return ResponseEntity.notFound().build();
        }

        teamRepository.delete(team);

        // 팀에 있는 멤버 모두 삭제

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData("팀 삭제 성공");

        return ResponseEntity.ok().body(message);
    }

    /**
     * 팀 확인 후 단일 팀 조회
     * - 전우진 2023.07.13
     */
    public ResponseEntity<Message> getTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElse(null);

        if(team != null) { // 팀이 존재하는 경우
            // 멤버
            List<TeamMember> teamMembers = team.getTeamMembers();

            List<TeamMemberResponseDTO> teamMemberResponseDTOList = teamMembers.stream()
                    .map(teamMember -> new TeamMemberResponseDTO(teamMember.getUser().getId(), teamMember.getTeam().getId(), teamMember.isTeamLeader()))
                    .collect(Collectors.toList());

            TeamResponseDTO teamResponseDTO = new TeamResponseDTO(
                    team.getId(),
                    team.getName(),
                    team.getPeopleNum(),
                    team.getState(),
                    teamMemberResponseDTOList
            );

            Message message = new Message();
            message.setStatus(StatusEnum.OK);
            message.setMessage("성공 코드");
            message.setData(teamResponseDTO);

            return ResponseEntity.ok().body(message);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 팀 확인 후 팀 전체 조회
     * - 전우진 2023.07.13
     */
    public ResponseEntity<Message> getAllTeam() {

        List<Team> teams = teamRepository.findAll();
        List<TeamResponseDTO> result = new ArrayList<>();

        for(Team team : teams) {

            List<TeamMemberResponseDTO> teamMemberResponseDTOList = team.getTeamMembers().stream()
                    .map(teamMember -> new TeamMemberResponseDTO(teamMember.getUser().getId(), teamMember.getTeam().getId(), teamMember.isTeamLeader()))
                    .collect(Collectors.toList());

            // 랭킹
            TeamRank teamRanks = team.getTeamRank();
            TeamRankResponseDTO teamRankResponseDTO = new TeamRankResponseDTO(teamRanks.getTeam().getId(), teamRanks.getCoin(), teamRanks.getTear());

            TeamResponseDTO teamResponseDTO = new TeamResponseDTO(
                    team.getId(),
                    team.getName(),
                    team.getPeopleNum(),
                    team.getState(),
                    teamRankResponseDTO,
                    teamMemberResponseDTOList
            );

            result.add(teamResponseDTO);

        }

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData(result);

        return ResponseEntity.ok().body(message);
    }

    /**
     * 가입된 팀 정보 조회 - 랭킹 포함
     * - 전우진 2023.07.15
     */
    public ResponseEntity<Message> getUserTeam(String userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        Long teamId = user.getTeam().getId();

        Team team = teamRepository.findById(teamId).orElse(null);

        if(team != null) {  // 팀이 존재하는 경우

            // 멤버
            List<TeamMember> teamMembers = team.getTeamMembers();
            List<TeamMemberResponseDTO> teamMemberResponseDTOList = teamMembers.stream()
                    .map(teamMember -> new TeamMemberResponseDTO(teamMember.getUser().getId(), teamMember.getTeam().getId(), teamMember.isTeamLeader()))
                    .collect(Collectors.toList());

            // 랭킹
            TeamRank teamRanks = team.getTeamRank();
            TeamRankResponseDTO teamRankResponseDTO = new TeamRankResponseDTO(teamRanks.getTeam().getId(), teamRanks.getCoin(), teamRanks.getTear());

            TeamResponseDTO teamResponseDTO = new TeamResponseDTO(
                    team.getId(),
                    team.getName(),
                    team.getPeopleNum(),
                    team.getState(),
                    teamRankResponseDTO,
                    teamMemberResponseDTOList
            );

            Message message = new Message();
            message.setStatus(StatusEnum.OK);
            message.setMessage("성공 코드");
            message.setData(teamResponseDTO);

            return ResponseEntity.ok().body(message);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
