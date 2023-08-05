package com.example.walkingmate_back.user.service

import com.example.walkingmate_back.user.dto.UserResponse
import com.example.walkingmate_back.user.entity.UserEntity
import com.example.walkingmate_back.user.repository.UserRepository
import jakarta.persistence.EntityManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.stereotype.Service
import java.util.*

class UserService2(

        private val userRepository: UserRepository,
        private var userResponse: UserResponse,
        private val entityManeger: EntityManager
){

    fun userInfo(userId: String?): Optional<UserEntity> {

        val user: Optional<UserEntity> = userRepository.findById(userId)
        return user
    }

    fun updateInfo(userId: String, newUser: UserEntity): UserResponse {

        // TODO user-info update
        val oldUser: UserEntity = entityManeger.find(UserEntity::class.java, userId)

        oldUser.name = newUser?.name?: oldUser.name
        oldUser.birth = newUser?.birth?: oldUser.birth
        oldUser.phone = newUser?.phone?: oldUser.phone
        oldUser.userBody.height = newUser?.userBody?.height?: oldUser.userBody.height
        oldUser.userBody.weight = newUser?.userBody?.weight?: oldUser.userBody.weight

        userResponse.data.code = userResponse.success
        userResponse.data.userId = userId
        userResponse.data.message = "user info update"
        return userResponse
    }

}