package com.example.walkingmate_back.board.repository;

import com.example.walkingmate_back.board.entity.Board;
import com.example.walkingmate_back.board.entity.BoardComment;
import com.example.walkingmate_back.user.entity.UserEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardCommentRepositoryTest {

    @Autowired
    BoardCommentRepository boardCommentRepository;

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
    @DisplayName("댓글을 반환하는지 확인")
    public void boardCommentListTest() {
        System.out.println("## boardCommentListTest 시작 ##");
        System.out.println();

        List<BoardComment> result = boardCommentRepository.findAll();

        assertEquals(result.size(), 1);
    }

}