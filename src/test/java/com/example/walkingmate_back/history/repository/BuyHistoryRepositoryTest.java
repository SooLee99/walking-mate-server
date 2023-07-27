package com.example.walkingmate_back.history.repository;

import com.example.walkingmate_back.board.repository.BoardCommentRepository;
import com.example.walkingmate_back.history.entity.BuyHistory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BuyHistoryRepositoryTest {

    @Autowired
    BuyHistoryRepository buyHistoryRepository;

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
    @DisplayName("사용자의 코인 구매 내역 조회 테스트")
    void findAllByUserId() {
        System.out.println("## findAllByUserId 시작 ##");
        System.out.println();

        List<BuyHistory> result = buyHistoryRepository.findAllByUserId("aaa");

        assertEquals(result.size(), 15);
    }
}