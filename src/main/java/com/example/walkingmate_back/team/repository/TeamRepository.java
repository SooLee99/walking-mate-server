package com.example.walkingmate_back.team.repository;

import com.example.walkingmate_back.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("select t from Team t where t.name LIKE %:teamName%")
    List<Team> findAllByName(@Param("teamName")String teamName);
}
