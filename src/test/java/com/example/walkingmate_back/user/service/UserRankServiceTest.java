package com.example.walkingmate_back.user.service;

import com.example.walkingmate_back.user.dto.UserRankResponseDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRankServiceTest {

    @Autowired
    private UserRankService userRankService;

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
    @DisplayName("사용자 랭킹 조회 테스트")
    void getUserRankTest() {
        System.out.println("## getUserRankTest 시작 ##");
        System.out.println();

        UserRankResponseDTO userRankResponseDTO = userRankService.getUserRank("aaa");

        assertEquals(userRankResponseDTO.getUserId(), "aaa");
        assertEquals(userRankResponseDTO.getCoin(), 19);
        assertEquals(userRankResponseDTO.getTear(), "실버");
    }

    @Test
    @DisplayName("개인 랭킹 조회 테스트")
    void getAllRankTest() {
        System.out.println("## getAllRankTest 시작 ##");
        System.out.println();

        List<UserRankResponseDTO> result = userRankService.getAllRank();

        assertEquals(result.size(), 2);
    }
}