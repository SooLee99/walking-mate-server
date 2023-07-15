package com.example.walkingmate_back.team.service;

import com.example.walkingmate_back.team.dto.TeamMemberResponseDTO;
import com.example.walkingmate_back.team.dto.TeamRankResponseDTO;
import com.example.walkingmate_back.team.dto.TeamRequestDTO;
import com.example.walkingmate_back.team.dto.TeamResponseDTO;
import com.example.walkingmate_back.team.entity.Team;
import com.example.walkingmate_back.team.entity.TeamMember;
import com.example.walkingmate_back.team.entity.TeamRank;
import com.example.walkingmate_back.team.repository.TeamMemberRepository;
import com.example.walkingmate_back.team.repository.TeamRankRepository;
import com.example.walkingmate_back.team.repository.TeamRepository;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *    팀 생성, 삭제, 단일 조회, 전체 조회, 가입된 팀 정보 조회
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.07.15
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamRankRepository teamRankRepository;

    /**
     * 사용자 확인 후 팀 생성
     * - 전우진 2023.07.13
     */
    public int saveTeam(TeamRequestDTO teamRequestDTO, String userId) {
        Optional<UserEntity> user = userRepository.findById(userId);

        if(user != null) {  // 사용자 확인
            if(user.get().getTeam() == null) {  // 기존 팀이 없는 경우
                Team team = new Team(teamRequestDTO.getName(), teamRequestDTO.getPeopleNum(), "모집");
                teamRepository.save(team);

                // 팀 가입 - 리더
                TeamMember teamMember = new TeamMember(user.get(), team, true);
                teamMemberRepository.save(teamMember);

                // 사용자 팀 아이디 추가

                return 1;
            } else {
                return -1; // 기존 팀이 이미 있으므로 생성 불가
            }
        } else { // 사용자 확인 불가
            return -2;
        }
    }

    /**
     * 팀 확인 후 팀 삭제
     * - 전우진 2023.07.13
     */
    public int deleteTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElse(null);

        if(team == null) { // 팀 존재하지 않는 경우
            return -1;
        }

        teamRepository.delete(team);

        // 팀에 있는 멤버 모두 삭제

        return 1;
    }

    /**
     * 팀 확인 후 단일 팀 조회
     * - 전우진 2023.07.13
     */
    public Optional<TeamResponseDTO> getTeam(Long teamId) {
        Optional<Team> result = teamRepository.findById(teamId);

        if(result.isPresent()) {
            Team team = result.get();

            // 멤버
            List<TeamMember> teamMembers = team.getTeamMembers();

            List<TeamMemberResponseDTO> teamMemberResponseDTOList = teamMembers.stream()
                    .map(teamMember -> new TeamMemberResponseDTO(teamMember.getUser().getId(), teamMember.getTeam().getId(), teamMember.isTeamLeader()))
                    .collect(Collectors.toList());

            return Optional.of(new TeamResponseDTO(
                    team.getId(),
                    team.getName(),
                    team.getPeopleNum(),
                    team.getState(),
                    teamMemberResponseDTOList
            ));
        } else {
            return Optional.empty();
        }
    }

    /**
     * 팀 확인 후 팀 전체 조회
     * - 전우진 2023.07.13
     */
    public List<TeamResponseDTO> getAllTeam() {

        List<Team> teams = teamRepository.findAll();
        List<TeamResponseDTO> result = new ArrayList<>();

        for(Team team : teams) {

            List<TeamMemberResponseDTO> teamMemberResponseDTOList = team.getTeamMembers().stream()
                    .map(teamMember -> new TeamMemberResponseDTO(teamMember.getUser().getId(), teamMember.getTeam().getId(), teamMember.isTeamLeader()))
                    .collect(Collectors.toList());

            TeamResponseDTO teamResponseDTO = new TeamResponseDTO(
                    team.getId(),
                    team.getName(),
                    team.getPeopleNum(),
                    team.getState(),
                    teamMemberResponseDTOList
            );

            result.add(teamResponseDTO);

        }
        return result;
    }

    /**
     * 가입된 팀 정보 조회 - 랭킹 포함
     * - 전우진 2023.07.15
     */
    public Optional<TeamResponseDTO> getUserTeam(String userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        Long teamId = user.get().getTeam().getId();

        Optional<Team> result = teamRepository.findById(teamId);

        if(result.isPresent()) {
            Team team = result.get();

            // 멤버
            List<TeamMember> teamMembers = team.getTeamMembers();

            List<TeamMemberResponseDTO> teamMemberResponseDTOList = teamMembers.stream()
                    .map(teamMember -> new TeamMemberResponseDTO(teamMember.getUser().getId(), teamMember.getTeam().getId(), teamMember.isTeamLeader()))
                    .collect(Collectors.toList());

            // 랭킹
            TeamRank teamRanks = team.getTeamRank();

            TeamRankResponseDTO teamRankResponseDTO = new TeamRankResponseDTO(teamRanks.getTeam().getId(), teamRanks.getCoin(), teamRanks.getTear());

            return Optional.of(new TeamResponseDTO(
                    team.getId(),
                    team.getName(),
                    team.getPeopleNum(),
                    team.getState(),
                    teamRankResponseDTO,
                    teamMemberResponseDTOList
            ));
        } else {
            return Optional.empty();
        }
    }
}
