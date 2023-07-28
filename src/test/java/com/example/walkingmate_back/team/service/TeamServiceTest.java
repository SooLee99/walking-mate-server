package com.example.walkingmate_back.team.service;

import com.example.walkingmate_back.team.dto.TeamRequestDTO;
import com.example.walkingmate_back.team.dto.TeamResponseDTO;
import com.example.walkingmate_back.team.entity.Team;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamServiceTest {

    @Autowired
    TeamService teamService;

    @Autowired
    UserService userService;

    @BeforeAll
    static void beforeAll() {
        System.out.println("## BeforeAll Annotation 호출 ##");
        System.out.println();
    }

    @AfterAll
    static void afterAll() {
        System.out.println("## afterAll Annotation 호출 ##");
        System.out.println();
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("## beforeEach Annotation 호출 ##");
        System.out.println();
    }

    @AfterEach
    void afterEach() {
        System.out.println("## afterEach Annotation 호출 ##");
        System.out.println();
    }

    @Test
    @DisplayName("팀 생성 테스트")
    void saveTeamTest() {
        System.out.println("## saveTeamTest 시작 ##");
        System.out.println();

        TeamRequestDTO teamRequestDTO = new TeamRequestDTO();
        teamRequestDTO.setName("ccc의 팀");
        teamRequestDTO.setPeopleNum(4);

        UserEntity user = userService.FindUser("ccc");

        TeamResponseDTO saveTeam = teamService.saveTeam(teamRequestDTO, user);

        assertEquals(saveTeam.getPeopleNum(), teamRequestDTO.getPeopleNum());
        assertEquals(saveTeam.getName(), teamRequestDTO.getName());
    }

    @Test
    @DisplayName("팀 삭제 테스트")
    void deleteTeamTest() {
        System.out.println("## deleteTeamTest 시작 ##");
        System.out.println();

        Team team = teamService.FindTeam(9L);
        String userId = "ccc";

        TeamResponseDTO deleteTeam = teamService.deleteTeam(team, userId);

        assertEquals(deleteTeam.getPeopleNum(), team.getPeopleNum());
        assertEquals(deleteTeam.getName(), team.getName());
    }

    @Test
    @DisplayName("팀 단일 조회 테스트")
    void getTeamTest() {
        System.out.println("## getTeamTest 시작 ##");
        System.out.println();

        Long teamId = 8L;

        TeamResponseDTO getTeam = teamService.getTeam(teamId);

        assertEquals(getTeam.getName(), "aaa의 팀");
        assertEquals(getTeam.getId(), teamId);
        assertEquals(getTeam.getPeopleNum(), 4);
    }

    @Test
    @DisplayName("팀 전체 조회 테스트")
    void getAllTeamTest() {
        System.out.println("## getAllTeamTest 시작 ##");
        System.out.println();

        List<TeamResponseDTO> getAllTeam = teamService.getAllTeam();

        assertEquals(getAllTeam.size(), 2);
    }

    @Test
    @DisplayName("사용자의 팀 조회 테스트")
    void getUserTeamTest() {
        System.out.println("## getUserTeamTest 시작 ##");
        System.out.println();

        String userId = "aaa";

        TeamResponseDTO getUserTeam = teamService.getUserTeam(userId);

        assertEquals(getUserTeam.getName(), "aaa의 팀");
        assertEquals(getUserTeam.getId(), 8L);
    }
}