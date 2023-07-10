package com.example.walkingmate_back.user.repository;

import com.example.walkingmate_back.user.entity.UserBank;
import com.example.walkingmate_back.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserBankRepository extends JpaRepository<UserBank, UserEntity> {

}
