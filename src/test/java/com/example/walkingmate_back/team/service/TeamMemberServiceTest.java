package com.example.walkingmate_back.team.service;

import com.example.walkingmate_back.team.dto.TeamMemberResponseDTO;
import com.example.walkingmate_back.user.entity.UserEntity;
import com.example.walkingmate_back.user.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamMemberServiceTest {

    @Autowired
    TeamMemberService teamMemberService;

    @Autowired
    UserService userService;

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
    @DisplayName("팀 멤버 생성 테스트")
    void saveMemberTest() {
        System.out.println("## saveMemberTest 시작 ##");
        System.out.println();

        Long teamId = 8L;
        UserEntity user = userService.FindUser("ccc");

        TeamMemberResponseDTO saveMember = teamMemberService.saveMember(teamId, user);

        assertEquals(saveMember.getTeamId(), teamId);
        assertEquals(saveMember.getUserId(), user.getId());
    }

    @Test
    @DisplayName("팀 멤버 삭제 테스트")
    void deleteTeamMemberTest() {
        System.out.println("## deleteTeamMemberTest 시작 ##");
        System.out.println();

        Long teamId = 8L;
        String userId = "ccc";

        TeamMemberResponseDTO deleteMember = teamMemberService.deleteTeamMember(teamId, userId);

        assertEquals(deleteMember.getTeamId(), teamId);
        assertEquals(deleteMember.getUserId(), userId);
    }
}