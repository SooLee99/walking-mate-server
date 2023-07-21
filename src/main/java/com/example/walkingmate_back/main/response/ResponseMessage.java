package com.example.walkingmate_back.main.response;

public class ResponseMessage {

    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String NOT_FOUND_USER = "회원 정보 조회 실패";

    public static final String WRITE_BOARD = "게시글 작성 성공";
    public static final String UPDATE_BOARD = "게시글 수정 성공";
    public static final String DELETE_BOARD = "게시글 삭제 성공";
    public static final String NOT_FOUND_BOARD = "게시글 조회 실패";

    public static final String WRITE_BOARDCOMMENT = "댓글 작성 성공";
    public static final String UPDATE_BOARDCOMMENT = "댓글 수정 성공";
    public static final String DELETE_BOARDCOMMENT = "댓글 삭제 성공";
    public static final String NOT_FOUND_BOARDCOMMENT = "댓글 조회 실패";

    public static final String WRITE_BATTLE = "대결 생성 성공";
    public static final String DELETE_BATTLE = "대결 삭제 성공";
    public static final String NOT_WRITE_BATTLE = "대결 생성 불가";
    public static final String CHECK_TEAM_BATTLE = "팀 대결 참여";
    public static final String NOT_FOUND_BATTLE = "대결 조회 실패";

    public static final String WRITE_BATTLERIVAL = "대결 라이벌 생성 성공";
    public static final String UPDATE_BATTLERIVAL = "대결 라이벌 걸음수 수정 성공";

    public static final String WRITE_TEAM = "팀 생성 성공";
    public static final String NOT_WRITE_TEAM = "팀 생성 불가";
    public static final String DELETE_TEAM = "팀 삭제 성공";
    public static final String NOT_FOUND_TEAM = "팀 조회 실패";
    public static final String NOT_FOUND_TEAMLEADER = "팀장이 아닌 경우";

    public static final String WRITE_TEAMRANK = "팀 랭킹 저장 성공";
    public static final String NOT_FOUND_TEAMRANK = "팀 랭킹 조회 실패";

    public static final String WRITE_TEAMMEMBER = "팀 멤버 가입 성공";
    public static final String DELETE_TEAMMEMBER = "팀 멤버 삭제 성공";

    public static final String WRITE_USERBODY = "신체정보 저장 성공";
    public static final String UPDATE_USERBODY = "신체정보 수정 성공";
    public static final String NOT_FOUND_USERBODY = "신체정보 조회 실패";

    public static final String WRITE_USERRANK = "사용자 랭킹 저장 성공";
    public static final String UPDATE_USERRANK = "사용자 랭킹 수정 성공";
    public static final String NOT_FOUND_USERRANK = "사용자 랭킹 조회 실패";

    public static final String WRITE_CHECKLIST = "체크리스트 작성 성공";
    public static final String UPDATE_CHECKLIST = "체크리스트 수정 성공";
    public static final String DELETE_CHECKLIST = "체크리스트 삭제 성공";
    public static final String CHECK_CHECKLIST = "체크리스트 선택 및 해제";
    public static final String NOT_FOUND_CHECKLIST = "체크리스트 조회 실패";

    public static final String WRITE_RUNRECORD = "운동기록 저장 성공";
    public static final String NOT_FOUND_RUNRECORD = "운동기록 조회 실패";

    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";

    public static final String READ_SUCCESS = "데이터베이스 조회 성공";
    public static final String DB_ERROR = "데이터베이스 에러";
}
