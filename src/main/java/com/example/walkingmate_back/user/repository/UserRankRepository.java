package com.example.walkingmate_back.user.repository;

import com.example.walkingmate_back.user.entity.UserRank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRankRepository extends JpaRepository<UserRank, String> {

}
