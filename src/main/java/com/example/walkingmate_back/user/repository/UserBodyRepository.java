package com.example.walkingmate_back.user.repository;

import com.example.walkingmate_back.user.entity.UserBody;
import com.example.walkingmate_back.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserBodyRepository extends JpaRepository<UserBody, String> {

}
