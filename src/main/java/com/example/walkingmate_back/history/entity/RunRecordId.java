package com.example.walkingmate_back.history.entity;

import com.example.walkingmate_back.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RunRecordId implements Serializable {

    private UserEntity user;

}
