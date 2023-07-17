package com.example.walkingmate_back.board.service;

import com.example.walkingmate_back.board.dto.BoardRequestDTO;
import com.example.walkingmate_back.board.entity.Board;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    BoardService boardService;

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

//    @Test
//    @DisplayName("게시글 저장 테스트")
//    public void createBoardTest() {
//        System.out.println("## createBoardTest 시작 ##");
//        System.out.println();
//
//        BoardRequestDTO boardRequestDTO = new BoardRequestDTO("aaa", "테스트 제목", "테스트 내용");
//        Board savedBoard = boardService.saveBoard(boardRequestDTO);
//
//        assertEquals(boardRequestDTO.getUserId(), savedBoard.getUser().getId());
//        assertEquals(boardRequestDTO.getTitle(), savedBoard.getTitle());
//        assertEquals(boardRequestDTO.getContent(), savedBoard.getContent());
//    }
}
