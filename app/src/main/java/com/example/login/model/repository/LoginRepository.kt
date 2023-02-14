package com.example.login.model.repository

import com.example.login.model.dataSource.LoginDataSource
import com.example.login.model.response.*
import com.example.login.utils.Result
import retrofit2.Response

class LoginRepository {
    private val loginDataSource = LoginDataSource()

    suspend fun postLogin(user: User): Result<LoginResponse> {
        return loginDataSource.postLogin(user)
    }

    suspend fun postSignUp(register: Register): Result<SignUpResponse> {
        return loginDataSource.postSignUp(register)
    }

    suspend fun test(): Response<testResponse> {
        return loginDataSource.postTest()
    }

    suspend fun postRecoverPass(email:Recover): Response<RecoverResponse> {
        return loginDataSource.postRecoverPass(email)
    }
}