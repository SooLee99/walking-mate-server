package com.example.walkingmate_back.team.repository;

import com.example.walkingmate_back.team.entity.TeamBattleHistory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamBattleHistoryRepositoryTest {

    @Autowired
    TeamBattleHistoryRepository teamBattleHistoryRepository;

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
    @DisplayName("팀별 대결 기록 조회 테스트")
    void findAllByTeamIdTest() {
        System.out.println("## findAllByTeamIdTest 시작 ##");
        System.out.println();

        List<TeamBattleHistory> result = teamBattleHistoryRepository.findAllByTeamId(8L);

        assertEquals(result.size(), 3);
    }
}