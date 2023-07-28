package com.example.walkingmate_back.history.service;

import com.example.walkingmate_back.history.dto.HomeResponseDTO;
import com.example.walkingmate_back.history.dto.RunRecordAVGDTO;
import com.example.walkingmate_back.history.dto.RunRecordRequestDTO;
import com.example.walkingmate_back.history.dto.RunRecordResponseDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RunRecordServiceTest {

    @Autowired
    RunRecordService runRecordService;

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
    @DisplayName("운동 기록 저장 테스트")
    void saveRunTest() throws ParseException {
        System.out.println("## saveRunTest 시작 ##");
        System.out.println();

        RunRecordRequestDTO runRecordRequestDTO = new RunRecordRequestDTO();
        runRecordRequestDTO.setDistance(10);
        runRecordRequestDTO.setStep(1000);

        String userId = "aaa";

        RunRecordResponseDTO saveRun = runRecordService.saveRun(runRecordRequestDTO, userId);

        assertEquals(saveRun.getUserId(), userId);
        assertEquals(saveRun.getStep(), runRecordRequestDTO.getStep());
        assertEquals(saveRun.getDistance(), runRecordRequestDTO.getDistance());
        assertEquals(saveRun.getDate(), LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    @Test
    @DisplayName("날짜별 운동 기록 조회 테스트")
    void getDateRunTest() {
        System.out.println("## saveRunTest 시작 ##");
        System.out.println();

        String userId = "aaa";
        String date = "20230728";

        List<RunRecordResponseDTO> getDateRun = runRecordService.getDateRun(userId, date);

        assertEquals(getDateRun.size(),1);
    }

    @Test
    @DisplayName("평균 운동 기록 조회 테스트")
    void getAllRunTest() {
        System.out.println("## saveRunTest 시작 ##");
        System.out.println();

        String userId = "aaa";

        List<RunRecordResponseDTO> getAllRun = runRecordService.getAllRun(userId);

        assertEquals(getAllRun.size(), 5);
    }

    @Test
    @DisplayName("금일 운동 기록 조회 테스트")
    void getDateRunHomeTest() {
        System.out.println("## saveRunTest 시작 ##");
        System.out.println();

        String userId = "aaa";

        HomeResponseDTO getDateRunHome = runRecordService.getDateRunHome(userId);

        assertEquals(getDateRunHome.getKcal(), 0);
        assertEquals(getDateRunHome.getDistance(), 10);
        assertEquals(getDateRunHome.getStep(), 1000);
    }

    @Test
    @DisplayName("평균 운동 기록 조회 테스트")
    void getRunAVGTest() {
        System.out.println("## saveRunTest 시작 ##");
        System.out.println();

        String userId = "aaa";

        RunRecordAVGDTO getRunAVG = runRecordService.getRunAVG(userId);

        assertEquals(getRunAVG.getNum(), 5);
        assertEquals(getRunAVG.getDistance(), 29);
        assertEquals(getRunAVG.getStep(), 662);
    }

    @Test
    @DisplayName("운동 기록 수정 테스트")
    void modifyRunTest() {
        System.out.println("## saveRunTest 시작 ##");
        System.out.println();

        Long id = 7L;
        RunRecordRequestDTO runRecordRequestDTO = new RunRecordRequestDTO();
        runRecordRequestDTO.setDistance(5);
        runRecordRequestDTO.setStep(500);

        RunRecordResponseDTO modifyRun = runRecordService.modifyRun(id, runRecordRequestDTO);

        assertEquals(modifyRun.getId(), id);
        assertEquals(modifyRun.getStep(), runRecordRequestDTO.getStep() + 2000);
        assertEquals(modifyRun.getDistance(), runRecordRequestDTO.getDistance() + 20);
    }
}