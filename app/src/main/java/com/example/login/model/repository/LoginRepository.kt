package com.example.login.model.repository

import android.app.Application
import com.example.login.model.dataSource.LoginDataSource
import com.example.login.model.response.*
import com.example.login.utils.Result
import retrofit2.Response

class LoginRepository (application: Application){
    private val loginDataSource = LoginDataSource(application)

    suspend fun postLogin(user: User): Result<LoginResponse> {
        return loginDataSource.postLogin(user)
    }

    suspend fun postSignUp(register: Register): Result<SignUpResponse> {
        return loginDataSource.postSignUp(register)
    }

    suspend fun postRecoverPass(email:Recover): Result<RecoverResponse> {
        return loginDataSource.postRecoverPass(email)
    }

    suspend fun saveTokenUser(token:String){
        loginDataSource.saveTokenUser(token)
    }
}