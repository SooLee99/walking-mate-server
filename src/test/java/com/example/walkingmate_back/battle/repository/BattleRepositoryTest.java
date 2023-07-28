package com.example.walkingmate_back.battle.repository;

import com.example.walkingmate_back.battle.entity.Battle;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BattleRepositoryTest {

    @Autowired
    BattleRepository battleRepository;

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
    @DisplayName("대결 검색 조회 테스트")
    void findAllByBattleRivalsByTeamNameTest() {
        System.out.println("## findAllByBattleRivalsByTeamNameTest 시작 ##");
        System.out.println();

        List<Battle> result = battleRepository.findAllByBattleRivalsByTeamName("aaa");

        assertEquals(result.size(), 1);
    }
}