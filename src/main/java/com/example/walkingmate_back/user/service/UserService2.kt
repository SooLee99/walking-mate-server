//package com.example.walkingmate_back.user.entity
//
//import com.example.walkingmate_back.user.dto.UserResponse
//import com.example.walkingmate_back.user.entity.UserEntity
//import com.example.walkingmate_back.user.repository.UserRepository
//import jakarta.persistence.EntityManager
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
//import org.springframework.stereotype.Service
//import java.util.*
//
//@Service
//class UserService2 @Autowired constructor(
//
//        private val userRepository: UserRepository,
//        private var userResponse: UserResponse
//
//){
//
//    fun userInfo(userId: String?): Optional<UserEntity> {
//
//        val user: Optional<UserEntity> = userRepository.findById(userId)
//        return user
//    }
//
//    fun updateInfo(userId: String, newUser: UserEntity): UserResponse {
//
//        // TODO user-info update
//        val oldUser = userRepository.findById(userId).get()
//        oldUser.name = newUser.name
//        oldUser.birth = newUser.birth
//        oldUser.phone = newUser.phone
//        userRepository.save(oldUser)
//
//        userResponse.data.code = userResponse.success
//        userResponse.data.userId = userId
//        userResponse.data.message = "user info update"
//        return userResponse
//    }
//
//}