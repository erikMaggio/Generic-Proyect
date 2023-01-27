package com.example.genericproyect.model.dataSource

import com.example.genericproyect.model.response.LoginResponse
import com.example.genericproyect.model.response.Register
import com.example.genericproyect.model.response.RegisterResponse
import com.example.genericproyect.model.response.User
import com.example.genericproyect.model.service.LoginService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginDataSource {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://7ef22df8-e9a0-4d59-8fb2-adb96dc299d1.mock.pstmn.io")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val serviceImp = retrofit.create(LoginService::class.java)

    suspend fun postLogin(user: User): Response<LoginResponse> {
        return serviceImp.postLogin(user)
    }

    suspend fun postRegister(register: Register): Response<RegisterResponse> {
        return serviceImp.postRegister(register)
    }

}