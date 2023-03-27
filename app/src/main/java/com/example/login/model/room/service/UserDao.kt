package com.example.login.model.room.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.login.model.room.entity.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM UserEntity")
    suspend fun getAllUser(): List<UserEntity>
}