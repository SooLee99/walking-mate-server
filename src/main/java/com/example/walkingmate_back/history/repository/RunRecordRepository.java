package com.example.walkingmate_back.history.repository;

import com.example.walkingmate_back.history.entity.RunRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface RunRecordRepository extends JpaRepository<RunRecord, Long> {

    // 운동 기록 조회 - 금일 운동 기록만 조회
    @Query("select d from RunRecord d where d.user.id LIKE %:userId% and d.date = :date order by d.id")
    List<RunRecord> findByUserIdWithDate(@Param("userId")String id, @Param("date") LocalDate date);

    // 사용자 아이디로 기록 조회
    List<RunRecord> findByUserId(String id);
}
