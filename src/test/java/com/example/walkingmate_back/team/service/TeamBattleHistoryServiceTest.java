package com.example.walkingmate_back.team.service;

import com.example.walkingmate_back.team.dto.TeamBattleRequestDTO;
import com.example.walkingmate_back.team.dto.TeamBattleResponseDTO;
import com.example.walkingmate_back.team.entity.Team;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamBattleHistoryServiceTest {

    @Autowired
    TeamBattleHistoryService teamBattleHistoryService;

    @Autowired
    TeamService teamService;

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
    @DisplayName("대결 기록 저장 테스트")
    void saveBattleTest() throws ParseException {
        System.out.println("## saveBattleTest 시작 ##");
        System.out.println();

        Team team = teamService.FindTeam(8L);
        TeamBattleRequestDTO teamBattleRequestDTO = new TeamBattleRequestDTO();
        teamBattleRequestDTO.setTeam(team);
        teamBattleRequestDTO.setBattleDate("20230730");
        teamBattleRequestDTO.setBetStep(1000);
        teamBattleRequestDTO.setVictory(true);

        TeamBattleResponseDTO saveBattle = teamBattleHistoryService.saveBattle(teamBattleRequestDTO);

        assertEquals(saveBattle.getTeam(), teamBattleRequestDTO.getTeam());
        assertEquals(saveBattle.getBattleDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")), teamBattleRequestDTO.getBattleDate());
        assertEquals(saveBattle.getBetStep(), teamBattleRequestDTO.getBetStep());

    }

    @Test
    @DisplayName("대결 기록 조회 테스트")
    void getTeamBattleTest() {
        System.out.println("## getTeamBattleTest 시작 ##");
        System.out.println();

        Long teamId = 8L;

        List<TeamBattleResponseDTO> getTeamBattle = teamBattleHistoryService.getTeamBattle(teamId);

        assertEquals(getTeamBattle.size(), 3);
    }
}