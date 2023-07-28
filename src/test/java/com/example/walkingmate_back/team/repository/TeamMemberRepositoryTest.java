package com.example.walkingmate_back.team.repository;

import com.example.walkingmate_back.team.entity.TeamMember;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamMemberRepositoryTest {

    @Autowired
    TeamMemberRepository teamMemberRepository;

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
    @DisplayName("사용자의 팀 멤버 조회 테스트")
    void findByUserIdTest() {
        System.out.println("## findByUserIdTest 시작 ##");
        System.out.println();

        TeamMember teamMember = teamMemberRepository.findByUserId("ccc");

        assertEquals(teamMember.getUser().getId(), "ccc");
        assertEquals(teamMember.getTeam().getId(), 8L);
    }
}