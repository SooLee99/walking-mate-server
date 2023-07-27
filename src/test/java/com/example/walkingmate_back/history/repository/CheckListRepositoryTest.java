package com.example.walkingmate_back.history.repository;

import com.example.walkingmate_back.board.repository.BoardCommentRepository;
import com.example.walkingmate_back.history.entity.CheckList;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CheckListRepositoryTest {

    @Autowired
    CheckListRepository checkListRepository;

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
    @DisplayName("날짜별 사용자 체크리스트 조회 테스트")
    void findByUserIdWithDate() {
        System.out.println("## findByUserIdWithDate 시작 ##");
        System.out.println();

        List<CheckList> result = checkListRepository.findByUserIdWithDate("aaa", LocalDate.now());

        assertEquals(result.size(), 4);
    }
}