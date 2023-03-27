package com.example.login.utils

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.login.model.room.entity.UserEntity
import com.example.login.model.room.service.UserDao

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDataBaseRoom : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        fun getInstance(application: Application): AppDataBaseRoom {
            return Room.databaseBuilder(
                application,
                AppDataBaseRoom::class.java,
                "user-bbdd"
            ).build()
        }
    }
}