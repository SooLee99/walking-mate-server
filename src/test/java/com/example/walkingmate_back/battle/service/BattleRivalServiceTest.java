package com.example.walkingmate_back.battle.service;

import com.example.walkingmate_back.battle.dto.BattleRivalResponseDTO;
import com.example.walkingmate_back.battle.dto.BattleRivalUpdateDTO;
import com.example.walkingmate_back.battle.entity.Battle;
import com.example.walkingmate_back.team.entity.TeamMember;
import com.example.walkingmate_back.team.service.TeamMemberService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BattleRivalServiceTest {

    @Autowired
    BattleRivalService battleRivalService;

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
    @DisplayName("대결라이벌 생성 테스트")
    void saveBattleRivaTest() {
        System.out.println("## saveBattleRivaTest 시작 ##");
        System.out.println();

        Battle battle = battleService.FindBattle(10L);
        TeamMember teamMember = teamMemberService.FindTeam("bbb");

        BattleRivalResponseDTO saveBattleRival = battleRivalService.saveBattleRival(battle, teamMember);

        assertEquals(saveBattleRival.getTeamId(), 7L);
        assertEquals(saveBattleRival.getStep(), 0);
    }

    @Test
    @DisplayName("대결라이벌 수정 테스트")
    void updateBattleRivalTest() {
        System.out.println("## updateBattleRivalTest 시작 ##");
        System.out.println();

        BattleRivalUpdateDTO battleRivalUpdateDTO = new BattleRivalUpdateDTO();
        battleRivalUpdateDTO.setStep(100);
        TeamMember teamMember = teamMemberService.FindTeam("bbb");
        Long battleId = 10L;

        BattleRivalResponseDTO updateBattleRival = battleRivalService.updateBattleRival(battleRivalUpdateDTO, battleId, teamMember);

        assertEquals(updateBattleRival.getTeamId(), 7L);
        assertEquals(updateBattleRival.getTeamName(), "bbb의 팀");
        assertEquals(updateBattleRival.getStep(), 100);
    }
}