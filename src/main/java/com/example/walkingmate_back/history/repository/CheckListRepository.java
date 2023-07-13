package com.example.walkingmate_back.history.repository;

import com.example.walkingmate_back.history.entity.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface CheckListRepository extends JpaRepository<CheckList, Long> {

    // 체크리스트 조회 - 특정 날짜의 체크리스트만 조회
    @Query("select d from CheckList d where d.user.id LIKE %:userId% and d.date = :date order by d.listId")
    List<CheckList> findByUserIdWithDate(@Param("userId")String id, @Param("date") LocalDate date);
}
