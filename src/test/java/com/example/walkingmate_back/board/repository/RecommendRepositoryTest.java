package com.example.walkingmate_back.board.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RecommendRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    RecommendRepository recommendRepository;

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
    @DisplayName("좋아요 존재 여부 확인")
    public void existingRecommendTest() {
        System.out.println("## existingRecommendTest 시작 ##");
        System.out.println();

        boolean existingRecommend = recommendRepository.existsByBoardIdAndUserId(12L, "aaa");

        assertEquals(existingRecommend, false);
    }

}
