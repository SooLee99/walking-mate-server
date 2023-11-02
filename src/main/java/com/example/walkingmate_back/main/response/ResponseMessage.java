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

    public static final String WRITE_RECOMMEND = "좋아요 저장 성공";

    public static final String WRITE_BATTLE = "대결 생성 성공";
    public static final String DELETE_BATTLE = "대결 삭제 성공";
    public static final String NOT_WRITE_BATTLE = "대결 생성 불가";
    public static final String CHECK_TEAM_BATTLE = "팀 대결 참여";
    public static final String NOT_FOUND_BATTLE = "대결 조회 실패";

    public static final String SUCCESS_BATTLE = "대결 진행 중";

    public static final String WRITE_BATTLERIVAL = "대결 라이벌 생성 성공";
    public static final String UPDATE_BATTLERIVAL = "대결 라이벌 걸음수 수정 성공";

    public static final String WRITE_TEAM = "팀 생성 성공";
    public static final String NOT_WRITE_TEAM = "팀 생성 불가";
    public static final String DELETE_TEAM = "팀 삭제 성공";
    public static final String MAX_TEAM = "팀 인원 초과";
    public static final String NOT_FOUND_TEAM = "팀 조회 실패";
    public static final String NOT_FOUND_TEAMLEADER = "팀장이 아닌 경우";
    public static final String NOT_WRITE_TEAMMEMBER = "기존 팀 존재";

    public static final String WRITE_TEAMBATTLE = "팀 대결 기록 저장 성공";

    public static final String READ_TEAMRANK = "팀 랭킹 조회 성공";
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

    public static final String WRITE_BUYHISTORY = "코인 구매 성공";
    public static final String WRITE_COIN = "코인 사용 성공";
    public static final String NOT_FOUND_BUYHISTORY = "코인 구매기록 조회 실패";
    public static final String NOT_COIN = "코인 부족";

    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";

    public static final String READ_SUCCESS = "데이터베이스 조회 성공";
    public static final String DB_ERROR = "데이터베이스 에러";

    public static final String LOGIN_MESSAGE = "로그인";
    public static final String LOGIN_FAIL = "로그인 실패";

    public static final String JOIN_MESSAGE = "회원가입";
    public static final String JOIN_FAIL = "회원가입 실패";

    public static final String PASSWORD_UPDATE = "비밀번호 재설정";
    public static final String PASSWORD_UPDATE_FAIL = "비밀번호 변경 실패";

    public static final String USER_INFO_SEARCH = "사용자 정보조회 성공";
    public static final String NOT_USER_INFO = "사용자 정보조회 실패";

    public static final String USER_UPDATE = "사용자 정보수정 성공";
    public static final String USER_UPDATE_FAIL = "사용자 정보수정 실패";

    public static final String USER_WITHDRAWAL_SUCCESS = "사용자 탈퇴 성공";

    public static final String USER_EMAIL_SUCESS = "인증번호 전송";
    public static final String USER_NUMBER_TRUE = "인증번호 일치";
    public static final String USER_NUMBER_FALSE = "인증번호 불일치";
}
