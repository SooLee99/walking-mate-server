package com.example.walkingmate_back.history.repository;

import com.example.walkingmate_back.history.entity.CheckList;
import com.example.walkingmate_back.history.entity.CheckListId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckListRepository extends JpaRepository<CheckList, CheckListId> {

}
