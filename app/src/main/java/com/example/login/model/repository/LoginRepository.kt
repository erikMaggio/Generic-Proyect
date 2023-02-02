package com.example.login.model.repository

import com.example.login.model.dataSource.LoginDataSource
import com.example.login.model.response.*
import retrofit2.Response

class LoginRepository {
    private val loginDataSource = LoginDataSource()

    suspend fun postLogin(user: User): Response<LoginResponse> {
        return loginDataSource.postLogin(user)
    }

    suspend fun postSignUp(register: Register): Response<SignUpResponse> {
        return loginDataSource.postSignUp(register)
    }

    suspend fun test(): Response<testResponse> {
        return loginDataSource.postTest()
    }
}