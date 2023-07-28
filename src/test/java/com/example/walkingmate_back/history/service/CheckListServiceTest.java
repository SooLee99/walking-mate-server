package com.example.walkingmate_back.history.service;

import com.example.walkingmate_back.history.dto.CheckListRequestDTO;
import com.example.walkingmate_back.history.dto.CheckListResponseDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CheckListServiceTest {

    @Autowired
    CheckListService checkListService;

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
    @DisplayName("체크리스트 저장 테스트")
    void saveCheckList() {
        System.out.println("## saveCheckList 시작 ##");
        System.out.println();

        CheckListRequestDTO checkListRequestDTO = new CheckListRequestDTO();
        checkListRequestDTO.setContent("20230727");
        String userId = "aaa";

        CheckListResponseDTO saveCheckList = checkListService.saveCheckList(checkListRequestDTO, userId);

        assertEquals(saveCheckList.getContent(), checkListRequestDTO.getContent());
        assertEquals(saveCheckList.isChecked(), false);
        assertEquals(saveCheckList.getDate(), LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        assertEquals(saveCheckList.getUserId(), userId);

    }

    @Test
    @DisplayName("체크리스트 수정 테스트")
    void updateCheckList() {
        System.out.println("## saveCheckList 시작 ##");
        System.out.println();

        CheckListRequestDTO checkListRequestDTO = new CheckListRequestDTO();
        checkListRequestDTO.setContent("update");

        Long id = 6L;

        CheckListResponseDTO updateCheckList = checkListService.updateCheckList(id, checkListRequestDTO);

        assertEquals(updateCheckList.getListId(), id);
        assertEquals(updateCheckList.getContent(), checkListRequestDTO.getContent());
    }

    @Test
    @DisplayName("체크리스트 체크 및 해제 테스트")
    void updateCheckd() {
        System.out.println("## saveCheckList 시작 ##");
        System.out.println();

        Long id = 6L;

        CheckListResponseDTO updateCheckList = checkListService.updateCheckd(id);

        assertEquals(updateCheckList.getListId(), id);
        assertEquals(updateCheckList.isChecked(), false);
    }

    @Test
    @DisplayName("체크리스트 삭제 테스트")
    void deleteCheckList() {
        System.out.println("## saveCheckList 시작 ##");
        System.out.println();

        Long id = 6L;

        CheckListResponseDTO updateCheckList = checkListService.deleteCheckList(id);

        assertEquals(updateCheckList.getListId(), id);
    }

    @Test
    @DisplayName("체크리스트 조회 테스트")
    void getDateCheckList() {
        System.out.println("## saveCheckList 시작 ##");
        System.out.println();

        String userId = "aaa";
        String date = "20230727";

        List<CheckListResponseDTO> getDateCheckList = checkListService.getDateCheckList(userId, date);

        assertEquals(getDateCheckList.size(), 3);
    }
}