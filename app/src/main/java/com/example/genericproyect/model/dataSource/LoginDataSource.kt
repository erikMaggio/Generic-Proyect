package com.example.genericproyect.model.dataSource

import com.example.genericproyect.model.response.LoginResponse
import com.example.genericproyect.model.service.LoginService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginDataSource {

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://7ef22df8-e9a0-4d59-8fb2-adb96dc299d1.mock.pstmn.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    suspend fun getLogin(email:String,password:String): Response<LoginResponse> {
        return retrofit().create(LoginService::class.java).getLogin(email,password)
    }
}