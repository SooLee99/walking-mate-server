package com.example.walkingmate_back.history.repository;

import com.example.walkingmate_back.history.entity.RunRecord;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RunRecordRepositoryTest {

    @Autowired
    RunRecordRepository runRecordRepository;

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
    @DisplayName("사용자의 금일 운동 기록 조회 테스트")
    void findByUserIdWithDate() {
        System.out.println("## saveRunTest 시작 ##");
        System.out.println();

        List<RunRecord> result = runRecordRepository.findByUserIdWithDate("aaa", LocalDate.now());

        assertEquals(result.size(), 1);
    }

    @Test
    @DisplayName("사용자의 운동 기록 조회 테스트")
    void findByUserId() {
        System.out.println("## saveRunTest 시작 ##");
        System.out.println();

        List<RunRecord> result = runRecordRepository.findByUserId("aaa");

        assertEquals(result.size(), 5);
    }
}