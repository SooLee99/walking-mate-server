package com.example.walkingmate_back.user.repository;

import com.example.walkingmate_back.user.entity.UserBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBankRepository extends JpaRepository<UserBank, String> {

}
