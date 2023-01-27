package com.example.genericproyect.model.service

import com.example.genericproyect.model.response.LoginResponse
import com.example.genericproyect.model.response.Register
import com.example.genericproyect.model.response.RegisterResponse
import com.example.genericproyect.model.response.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {

    @POST("/login?")
    suspend fun postLogin(
        @Body user: User
    ): Response<LoginResponse>

    @POST("/register")
    suspend fun postRegister(
        @Body register : Register
    ) : Response<RegisterResponse>
}

