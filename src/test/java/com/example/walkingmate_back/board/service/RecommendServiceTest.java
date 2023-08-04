package com.example.walkingmate_back.board.service;

import com.example.walkingmate_back.board.dto.BoardResponseDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RecommendServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    RecommendService recommendService;

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
    @DisplayName("좋아요 저장 테스트")
    public void createRecommendTest() {
        System.out.println("## createRecommendTest 시작 ##");
        System.out.println();
        //given
        String userId ="aaa";
        Long boardId = 12L;
        //when
        BoardResponseDTO saveRecommend = recommendService.saveRecommend(boardId, userId);
        //then
        assertEquals(saveRecommend.getUserId(), "aaa");
        System.out.println(saveRecommend.getRecommend());

    }
}
