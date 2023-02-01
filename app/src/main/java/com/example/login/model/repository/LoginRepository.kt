package com.example.login.model.repository

import com.example.login.model.dataSource.LoginDataSource
import com.example.login.model.response.LoginResponse
import com.example.login.model.response.Register
import com.example.login.model.response.SignUpResponse
import com.example.login.model.response.User
import retrofit2.Response

class LoginRepository {
    private val loginDataSource = LoginDataSource()

    suspend fun postLogin(user: User): Response<LoginResponse> {
        return loginDataSource.postLogin(user)
    }

    suspend fun postSignUp(register: Register): Response<SignUpResponse> {
        return loginDataSource.postSignUp(register)
    }
}