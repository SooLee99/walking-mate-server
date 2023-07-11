package com.example.walkingmate_back.history.repository;

import com.example.walkingmate_back.history.entity.RunRecord;
import com.example.walkingmate_back.history.entity.RunRecordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RunRecordRepository extends JpaRepository<RunRecord, String> {

    @Query("select d from RunRecord d where d.user.id LIKE %:userId% and d.date = :date")
    List<RunRecord> findByUserIdWithDate(@Param("userId")String id, @Param("date") LocalDate date);

    RunRecord findByDate(LocalDate date);
}
