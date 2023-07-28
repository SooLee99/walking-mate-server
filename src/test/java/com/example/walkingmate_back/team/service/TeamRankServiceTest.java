package com.example.walkingmate_back.team.service;

import com.example.walkingmate_back.team.dto.TeamRankResponseDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamRankServiceTest {

    @Autowired
    TeamRankService teamRankService;

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
    @DisplayName("팀 랭킹 조회 테스트")
    void getAllRankTest() {
        System.out.println("## getAllRankTest 시작 ##");
        System.out.println();

        List<TeamRankResponseDTO> getAllRank = teamRankService.getAllRank();

         assertEquals(getAllRank.size(), 2);
    }
}