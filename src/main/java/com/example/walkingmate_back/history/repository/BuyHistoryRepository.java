package com.example.walkingmate_back.history.repository;

import com.example.walkingmate_back.history.entity.BuyHistory;
import com.example.walkingmate_back.history.entity.BuyHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyHistoryRepository extends JpaRepository<BuyHistory, BuyHistoryId> {

}
