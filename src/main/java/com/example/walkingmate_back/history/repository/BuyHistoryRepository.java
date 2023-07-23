package com.example.walkingmate_back.history.repository;

import com.example.walkingmate_back.history.entity.BuyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuyHistoryRepository extends JpaRepository<BuyHistory, Long> {

    List<BuyHistory> findAllByUserId(String userId);
}
