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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *    팀 생성, 삭제, 단일 조회, 전체 조회, 가입된 팀 정보 조회
 *    - 서비스 로직
 *
 *   @version          1.00 / 2023.08.01
 *   @author           전우진
 */

@Service
@RequiredArgsConstructor
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamRankRepository teamRankRepository;

    /**
     * 사용자 확인 후 팀 생성
     * - 전우진 2023.07.13
     */
    public TeamResponseDTO saveTeam(TeamRequestDTO teamRequestDTO, UserEntity user) {
        if(teamMemberRepository.findByUserId(user.getId()) == null) {  // 기존 팀이 없는 경우
            Team team = new Team(teamRequestDTO.getName(), teamRequestDTO.getIntro(), teamRequestDTO.getPeopleNum(), "모집");
            teamRepository.save(team);

            // 팀 가입 - 리더
            TeamMember teamMember = new TeamMember(user, team, true);
            teamMemberRepository.save(teamMember);

            // 팀 랭킹 추가
            TeamRank teamRank = new TeamRank(team.getId(), 0, "실버");
            teamRankRepository.save(teamRank);

            return TeamResponseDTO.builder()
                    .id(team.getId())
                    .name(team.getName())
                    .intro(team.getIntro())
                    .peopleNum(team.getPeopleNum())
                    .state(team.getState())
                    .build();
        } else {
            // 기존 팀이 이미 있으므로 생성 불가
            return null;
        }
    }

    /**
     * 팀 확인 후 팀 삭제
     * - 전우진 2023.07.13
     */
    public TeamResponseDTO deleteTeam(Team team, String userId) {
        TeamMember teamMember = teamMemberRepository.findByUserId(userId);
        if (teamMember.isTeamLeader() == true) {  // 팀 리더인 경우

            teamRepository.delete(team);

            return TeamResponseDTO.builder()
                    .id(team.getId())
                    .name(team.getName())
                    .intro(team.getIntro())
                    .peopleNum(team.getPeopleNum())
                    .state(team.getState())
                    .build();
        }
        else { // 팀 리더가 아닌 경우
            return null;
        }
    }

    /**
     * 팀 확인 후 단일 팀 조회
     * - 전우진 2023.07.13
     */
    public TeamResponseDTO getTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElse(null);

        if(team != null) { // 팀이 존재하는 경우
            // 멤버
            List<TeamMember> teamMembers = team.getTeamMembers();

            List<TeamMemberResponseDTO> teamMemberResponseDTOList = teamMembers.stream()
                    .map(teamMember -> new TeamMemberResponseDTO(teamMember.getUser().getId(), teamMember.getTeam().getId(), teamMember.isTeamLeader()))
                    .collect(Collectors.toList());

            // 랭킹
            TeamRank teamRanks = team.getTeamRank();
            TeamRankResponseDTO teamRankResponseDTO = new TeamRankResponseDTO(teamRanks.getTeam().getId(), teamRanks.getCoin(), teamRanks.getTear());

            return TeamResponseDTO.builder()
                    .id(team.getId())
                    .name(team.getName())
                    .intro(team.getIntro())
                    .peopleNum(team.getPeopleNum())
                    .state(team.getState())
                    .teamMemberResponseDTOList(teamMemberResponseDTOList)
                    .teamRankResponseDTO(teamRankResponseDTO)
                    .build();
        } else {
            return null;
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

            // 랭킹
            TeamRank teamRanks = team.getTeamRank();
            TeamRankResponseDTO teamRankResponseDTO = new TeamRankResponseDTO(teamRanks.getTeam().getId(), teamRanks.getCoin(), teamRanks.getTear());

            TeamResponseDTO teamResponseDTO = new TeamResponseDTO(
                    team.getId(),
                    team.getName(),
                    team.getIntro(),
                    team.getPeopleNum(),
                    team.getState(),
                    teamRankResponseDTO,
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
    public TeamResponseDTO getUserTeam(String userId) {
        Long teamId = teamMemberRepository.findByUserId(userId).getTeam().getId();

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

            return TeamResponseDTO.builder()
                    .id(team.getId())
                    .name(team.getName())
                    .intro(team.getIntro())
                    .peopleNum(team.getPeopleNum())
                    .state(team.getState())
                    .teamMemberResponseDTOList(teamMemberResponseDTOList)
                    .teamRankResponseDTO(teamRankResponseDTO)
                    .build();
        } else {
            return null;
        }
    }

    public Team FindTeam(Long teamId){
        return teamRepository.findById(teamId).orElse(null);
    }
}
