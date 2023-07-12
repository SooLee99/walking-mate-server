package com.example.walkingmate_back.history.repository;

import com.example.walkingmate_back.history.entity.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckListRepository extends JpaRepository<CheckList, Long> {

}
