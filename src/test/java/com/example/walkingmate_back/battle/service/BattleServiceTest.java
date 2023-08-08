package com.example.walkingmate_back.battle.service;

import com.example.walkingmate_back.battle.dto.BattleRequestDTO;
import com.example.walkingmate_back.battle.dto.BattleResponseDTO;
import com.example.walkingmate_back.battle.dto.BattleSearchDTO;
import com.example.walkingmate_back.team.entity.TeamMember;
import com.example.walkingmate_back.team.service.TeamMemberService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BattleServiceTest {

    @Autowired
    BattleService battleService;

    @Autowired
    TeamMemberService teamMemberService;

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
    @DisplayName("대결 생성 테스트")
    void saveBattleTest() throws ParseException {
        System.out.println("## saveBattleTest 시작 ##");
        System.out.println();

        TeamMember teamMember = teamMemberService.FindTeam("aaa");

        BattleResponseDTO saveBattle = battleService.saveBattle(teamMember);

        assertEquals(saveBattle.getTotalStep(), 0);
    }

    @Test
    @DisplayName("대결 삭제 테스트")
    void deleteBattleTest() {
        System.out.println("## deleteBattleTest 시작 ##");
        System.out.println();

        Long battleId = 10L;

        BattleResponseDTO deleteBattle = battleService.deleteBattle(battleId);

        assertEquals(deleteBattle.getId(), battleId);
    }

    @Test
    @DisplayName("대결 전체 조회 테스트")
    void getAllBattleTest() {
        System.out.println("## getAllBattleTest 시작 ##");
        System.out.println();

        List<BattleResponseDTO> getAllBattle = battleService.getAllBattle();

        assertEquals(getAllBattle.size(), 1);
    }

    @Test
    @DisplayName("대결 단일 조회 테스트")
    void getBattleTest() {
        System.out.println("## getBattleTest 시작 ##");
        System.out.println();

        Long battleId = 13L;

        BattleResponseDTO getBattle = battleService.getBattle(battleId);

        assertEquals(getBattle.getId(), battleId);
        assertEquals(getBattle.getTotalStep(), 0);
        assertEquals(getBattle.getStartDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")), "20230731");
    }

    @Test
    @DisplayName("대결 검색 조회 테스트")
    void getSearchBattleTest() {
        System.out.println("## getSearchBattleTest 시작 ##");
        System.out.println();

        BattleSearchDTO battleSearchDTO = new BattleSearchDTO();
        battleSearchDTO.setSearch("a");

        List<BattleResponseDTO> getSearchBattle = battleService.getSearchBattle(battleSearchDTO);

        assertEquals(getSearchBattle.size(), 1);
    }

    @Test
    @DisplayName("대결 종료 테스트")
    void finishBattleTest() {
        System.out.println("## finishBattleTest 시작 ##");
        System.out.println();

        Long battleId = 14L;

        BattleResponseDTO finishBattle = battleService.finishBattle(battleId);

        assertEquals(finishBattle.getId(), battleId);
    }
}