package com.example.genericproyect.model.repository

import com.example.genericproyect.model.dataSource.LoginDataSource
import com.example.genericproyect.model.response.LoginResponse
import retrofit2.Response

class LoginRepository {
    private val getLoginDataSource = LoginDataSource()

    suspend fun getLogin(email:String,password:String): Response<LoginResponse> {
        return getLoginDataSource.getLogin(email,password)
    }
}