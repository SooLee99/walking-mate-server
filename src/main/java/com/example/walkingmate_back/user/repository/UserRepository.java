package com.example.walkingmate_back.user.repository;

import com.example.walkingmate_back.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

}
