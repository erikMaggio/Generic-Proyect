package com.example.genericproyect.model.repository

import com.example.genericproyect.model.dataSource.LoginDataSource
import com.example.genericproyect.model.response.LoginResponse
import com.example.genericproyect.model.response.Register
import com.example.genericproyect.model.response.RegisterResponse
import com.example.genericproyect.model.response.User
import retrofit2.Response

class LoginRepository {
    private val loginDataSource = LoginDataSource()

    suspend fun postLogin(user:User): Response<LoginResponse> {
        return loginDataSource.postLogin(user)
    }

    suspend fun postRegister(register: Register): Response<RegisterResponse> {
        return loginDataSource.postRegister(register)
    }
}