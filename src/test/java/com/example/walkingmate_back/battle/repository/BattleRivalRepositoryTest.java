package com.example.walkingmate_back.battle.repository;

import com.example.walkingmate_back.battle.entity.BattleRival;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BattleRivalRepositoryTest {

    @Autowired
    BattleRivalRepository battleRivalRepository;

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
    @DisplayName("팀 아이디로 대결 여부 조회 테스트")
    void findByTeamIdTest() {
        System.out.println("## findByTeamIdTest 시작 ##");
        System.out.println();

        BattleRival battleRival = battleRivalRepository.findByTeamId(7L);

        assertEquals(battleRival.getTeam().getId(), 7L);
    }
}