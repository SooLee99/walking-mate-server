package com.example.walkingmate_back.team.repository;

import com.example.walkingmate_back.team.entity.Team;
import com.example.walkingmate_back.team.entity.TeamBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface TeamBankRepository extends JpaRepository<TeamBank, Long> {

}
